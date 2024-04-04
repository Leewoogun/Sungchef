from django.shortcuts import render
from django.http.response import JsonResponse
from .models import Recommend
import os
os.environ["SPARK_HOME"] = "/root/cluster/spark"
import findspark
from pyspark.sql import SparkSession
from pyspark.sql.functions import lit, collect_list, concat_ws, col
# import pymysql
# from sqlalchemy import create_engine
import pandas as pd
from sklearn.neighbors import NearestNeighbors
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.decomposition import TruncatedSVD
import operator
import random

recipe_matrix = []
recipe_df = []

# 추천 받기
def similar_item(request, suser_sns_id, recent):
    global recipe_matrix
    global recipe_df
    foods = [
        18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 52,
        53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 69, 70, 71, 72, 73, 74, 75, 82, 83, 84, 86, 87, 88, 89, 90, 91, 92, 93,
        94, 95, 96, 98, 99, 100, 101, 102, 103, 104, 106, 107, 108, 109, 110, 111, 113, 114, 115, 116, 117, 118, 119, 120, 121, 123, 125,
        126, 127, 128, 129, 131, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 146, 148, 149, 150, 151, 152, 153, 154, 155,
        156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 168, 169, 171, 172, 173, 174, 176, 177, 178, 179, 181, 182, 183, 184, 185,
        186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 202, 203, 205, 206, 207, 208, 210, 211, 212, 213, 214,
        215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 233, 234, 235, 236, 237, 238, 240, 241, 242, 243,
        244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266, 267, 268, 269,
        270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 285, 286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 296,
        297, 298, 299, 300, 309, 310, 311, 312, 313, 314
        ]   
    if Recommend.objects.filter(suser_sns_id=suser_sns_id).filter(recommend_type=1).count() != 10:
        
        recommends = Recommend.objects.filter(suser_sns_id=suser_sns_id)
        recommends.delete()
        
        findspark.init()
        # PySpark 세션 생성
        spark = SparkSession.builder \
            .appName("MySQL Integration") \
            .config("spark.jars", "") \
            .getOrCreate()

        # MySQL 연결 정보
        jdbc_url = "jdbc:mysql://db서버:db포트/recipe-service?characterEncoding=UTF-8"
        connection_properties = {
            "user": "root",
            "password": "비밀번호",
            "driver": "com.mysql.cj.jdbc.Driver"  # MySQL Connector/J 8.x 사용 시
        }

        # recipe_make_log 테이블 불러오기
        df_recipe_make_log = spark.read.jdbc(url=jdbc_url,
                                            table="recipe_make_log",
                                            properties=connection_properties)

        # recipe 테이블 불러오기
        df_recipe = spark.read.jdbc(url=jdbc_url,
                                    table="recipe",
                                    properties=connection_properties)

        # 두 DataFrame을 조인하기
        # 예시에서는 recipe_id를 기준으로 내부 조인(inner join)을 수행합니다.
        df_joined = df_recipe_make_log.join(df_recipe,
                                            df_recipe_make_log.recipe_id == df_recipe.recipe_id,
                                            "left")

        # 레이팅 컬럼 추가
        df_joined = df_joined.withColumn("rating", lit(1))

        # 작업이 끝난 후 Spark 세션 종료
        spark.stop()
        
        log = df_joined.toPandas()
        
        # 성능 문제로 구현 못 함
        age_recommend_list = random.sample(foods, 10)
        gender_recommend_list = random.sample(foods, 10)
        weather_recommend_list = random.sample(foods, 10)
        
        for el in age_recommend_list:
            recommend = Recommend(suser_sns_id=suser_sns_id, recommend_id=el, recommend_type=3)
            recommend.save()
        for el in gender_recommend_list:
            recommend = Recommend(suser_sns_id=suser_sns_id, recommend_id=el, recommend_type=2)
            recommend.save()
        for el in weather_recommend_list:
            recommend = Recommend(suser_sns_id=suser_sns_id, recommend_id=el, recommend_type=4)
            recommend.save()
        
        # # 유저 기반
        rating_matrix = log.pivot_table(index='suser_sns_id', columns='food_id', values='rating').fillna(0)
        
        # 모델 초기화 및 피팅
        model_knn = NearestNeighbors(metric='cosine', algorithm='brute', n_neighbors=20, n_jobs=-1)
        model_knn.fit(rating_matrix)

        # 특정 사용자의 인덱스 찾기
        user_index = rating_matrix.index.tolist().index(suser_sns_id)
        
        # 특정 사용자에 대한 가장 가까운 이웃 찾기
        distances, indices = model_knn.kneighbors(rating_matrix.iloc[user_index,:].values.reshape(1, -1), n_neighbors=5)
        
        # 유사한 사용자들의 인덱스를 사용하여 추천 생성
        # 예시로, 유사한 사용자들이 고른 음식들 중 가장 많이 선택된 상위 N개를 추천 아이템으로 선택
        similar_users = rating_matrix.index[indices.flatten()].tolist()
        filtered_logs = log[log['suser_sns_id'].isin(similar_users)]
        recommend_list = filtered_logs['food_id'].value_counts().head(10).index.tolist()
        for el in recommend_list:
            recommend = Recommend(suser_sns_id=suser_sns_id, recommend_id=el, recommend_type=1)
            recommend.save()

    
    else:
        tmp = Recommend.objects.filter(suser_sns_id=suser_sns_id).filter(recommend_type=1)
        recommend_list = [el.recommend_id for el in tmp]
        
        tmp = Recommend.objects.filter(suser_sns_id=suser_sns_id).filter(recommend_type=2)
        gender_recommend_list = [el.recommend_id for el in tmp]
        
        tmp = Recommend.objects.filter(suser_sns_id=suser_sns_id).filter(recommend_type=3)
        age_recommend_list = [el.recommend_id for el in tmp]
        
        tmp = Recommend.objects.filter(suser_sns_id=suser_sns_id).filter(recommend_type=4)
        weather_recommend_list = [el.recommend_id for el in tmp]
        
    tmp = Recommend.objects.filter(suser_sns_id=suser_sns_id).filter(recommend_type=0)
    fridge_recommend_list = [el.recommend_id for el in tmp]
        
        
    # 최근에 먹은 음식 기반 추천
    if len(recipe_matrix) == 0:
        # SparkSession 초기화
        spark = SparkSession.builder \
            .appName("MySQL Integration") \
            .config("spark.jars", "/path/to/mysql-connector-java.jar") \
            .getOrCreate()

        # MySQL 연결 정보
        jdbc_url = "jdbc:mysql://db주소:db포트/recipe-service?characterEncoding=UTF-8"
        connection_properties = {
            "user": "root",
            "password": "비밀번호",
            "driver": "com.mysql.cj.jdbc.Driver"
        }

        # recipe_ingredient 테이블 불러오기
        df_recipe_ingredient = spark.read.jdbc(
            url=jdbc_url, 
            table="`ingredient-service`.recipe_ingredient",
            properties=connection_properties
        )

        # ingredient_sample 테이블 불러오기
        df_ingredient_sample = spark.read.jdbc(
            url=jdbc_url, 
            table="`ingredient-service`.ingredient_sample",
            properties=connection_properties
        )

        # recipe 테이블 불러오기
        df_recipe = spark.read.jdbc(
            url=jdbc_url, 
            table="`recipe-service`.recipe",
            properties=connection_properties
        )

        # 조인 수행
        df_joined = df_recipe_ingredient.join(
            df_ingredient_sample, 
            df_recipe_ingredient.recipe_show_name_id == df_ingredient_sample.recipe_show_name_id
        ).join(
            df_recipe, 
            df_recipe.recipe_id == df_recipe_ingredient.recipe_id
        )

        # GROUP_CONCAT 대신 collect_list를 사용하여 재료 이름을 합치고, 이후 문자열로 변환

        df_grouped = df_joined.groupBy("recipe_id").agg(
            concat_ws(", ", collect_list("convert_name")).alias("ingredient_names"),
            "food_id",
            "recipe_main_ingredient",
            "recipe_method"
        )

        # 'combined' 컬럼 생성
        df_final = df_grouped.withColumn(
            "combined", 
            concat_ws(" ", col("ingredient_names"), col("recipe_main_ingredient"), col("recipe_method"))
        )

        # 결과 확인
        df_final.show()

        # 작업이 끝난 후 Spark 세션 종료
        spark.stop()
        
        recipe_df = df_final.toPandas()
        
        vectorizer = TfidfVectorizer()
        matrix = vectorizer.fit_transform(recipe_df['combined'])
        recipe_matrix = cosine_similarity(matrix, matrix)
    
    recent_index = recipe_df.index[recipe_df["recipe_id"] == int(recent)]
    sim_recipes = recipe_matrix[recent_index][0]
    sim_recipe_list = []
    for i in range(len(sim_recipes)):
        food_id = recipe_df.loc[i]['food_id']
        if food_id in foods:
            sim_recipe_list.append([sim_recipes[i], recipe_df.loc[i]['food_id']])
            
    sim_recipe_list.sort(reverse=True)
    recent_set = set()
    idx = 0
    while len(recent_set) < 10:
        recent_set.add(int(sim_recipe_list[idx][1]))
        idx += 1
    recent_list = list(recent_set)
    return JsonResponse({"recipeIdList":fridge_recommend_list, "foodIdList":recent_list+recommend_list+gender_recommend_list+age_recommend_list+weather_recommend_list})


def data_delete(request, suser_sns_id):
    recommends = Recommend.objects.filter(suser_sns_id=suser_sns_id)
    recommends.delete()
    return JsonResponse({'mgs':f'suser_sns_id={suser_sns_id}인 유저 추천 데이터 삭제'})


def data_all_delete(request):
    recommends = Recommend.objects.all()
    recommends.delete()
    return JsonResponse({'mgs':'모든 유저 추천 데이터 삭제'})

    
def ingredient(request, suser_sns_id):
    tmp = Recommend.objects.filter(suser_sns_id=suser_sns_id).filter(recommend_type=0)
    tmp.delete()
    
    spark = SparkSession.builder \
    .appName("MySQL to Pandas DF Conversion") \
    .config("spark.jars", "/path/to/mysql-connector-java.jar") \
    .getOrCreate()

    # MySQL 연결 정보 설정
    jdbc_url = "jdbc:mysql://db주소:port/recipe-service?characterEncoding=UTF-8"
    connection_properties = {
        "user": "root",
        "password": "비밀번호",
        "driver": "com.mysql.cj.jdbc.Driver"
    }

    # 각 테이블 불러오기
    df_recipe_ingredient = spark.read.jdbc(url=jdbc_url, table="`ingredient-service`.recipe_ingredient", properties=connection_properties)
    df_ingredient_sample = spark.read.jdbc(url=jdbc_url, table="`ingredient-service`.ingredient_sample", properties=connection_properties)
    df_fridge = spark.read.jdbc(url=jdbc_url, table="`fridge-service`.fridge", properties=connection_properties)

    # 조인 작업
    # recipe_ingredient와 ingredient_sample 조인
    df_joined_ingredients = df_recipe_ingredient.join(df_ingredient_sample, df_recipe_ingredient["recipe_show_name_id"] == df_ingredient_sample["recipe_show_name_id"], "inner")

    # GROUP_CONCAT 대신 concat_ws 사용하여 재료 이름 문자열로 합치기
    df_grouped_ingredients = df_joined_ingredients.groupBy("recipe_id").agg(concat_ws(", ", "convert_name").alias("ingredient_names"))

    # 사용자의 냉장고 데이터와 조인
    suser_sns_id = 1  # 예시 사용자 ID
    df_my_ingredient = df_fridge.filter(col("suser_sns_id") == suser_sns_id).join(df_ingredient_sample, df_fridge["ingredient_id"] == df_ingredient_sample["ingredient_id"], "inner").select("ingredient_name").distinct()

    # Spark 세션 종료
    spark.stop()
        
    # Pandas DataFrame으로 변환
    ingredient = df_grouped_ingredients.toPandas()
    my_ingredient = df_my_ingredient.toPandas()
    
    # 재료 기반 추천
    if my_ingredient.loc[0]["ingredient_names"] != None:
        total = pd.concat([my_ingredient, ingredient], ignore_index=True)
        
        # TF-IDF 변환 및 차원 축소
        vectorizer = TfidfVectorizer()
        tfidf_matrix = vectorizer.fit_transform(total['ingredient_names'])
        svd = TruncatedSVD(n_components=100)  # 차원을 100으로 축소
        tfidf_matrix_reduced = svd.fit_transform(tfidf_matrix)
        
        # 차원 축소된 데이터를 사용하여 코사인 유사도 계산
        cosine_sim = cosine_similarity(tfidf_matrix_reduced, tfidf_matrix_reduced)
        
        sim_list = []
        for i in range(1, len(cosine_sim[0])):
            sim_list.append([cosine_sim[0][i], ingredient.loc[i-1]["recipe_id"]])
        sim_list.sort(reverse=True)
        
        fridge_recommend_list = [int(sim_list[i][1]) for i in range(10)]
    else:
        fridge_recommend_list = []
    for el in fridge_recommend_list:
        recommend = Recommend(suser_sns_id=suser_sns_id, recommend_id=el, recommend_type=0)
        recommend.save()
    return JsonResponse({'mgs':'재료기반 추천 생성'})
    

    
