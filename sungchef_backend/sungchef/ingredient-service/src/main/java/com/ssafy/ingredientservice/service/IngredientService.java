package com.ssafy.ingredientservice.service;

import com.ssafy.ingredientservice.db.entity.Ingredient;
import com.ssafy.ingredientservice.db.repository.IngredientRepository;
import com.ssafy.ingredientservice.db.repository.RecipeIngredientRepository;
import com.ssafy.ingredientservice.dto.request.ConvertImageReq;
import com.ssafy.ingredientservice.dto.request.IngredientListReq;
import com.ssafy.ingredientservice.dto.response.RecipeIngredientInfo;
import com.ssafy.ingredientservice.dto.response.IngredientInfo;
import com.ssafy.ingredientservice.dto.response.RecipeIngredient;
import com.ssafy.ingredientservice.dto.response.IngredientRes;
import com.ssafy.ingredientservice.dto.response.RecipeIngredientListRes;;
import com.ssafy.ingredientservice.dto.response.IngredientListRes;
import com.ssafy.ingredientservice.exception.exception.IngredientNotFoundException;
import com.ssafy.ingredientservice.exception.exception.RecipeNotFoundException;
import com.ssafy.ingredientservice.service.client.RecipeServiceClient;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class IngredientService {

    private final ResponseService responseService;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeServiceClient recipeServiceClient;

    public ResponseEntity<?> getUsedIngredientsInRecipe(Integer recipeId) throws IngredientNotFoundException, RecipeNotFoundException {
        List<com.ssafy.ingredientservice.db.entity.RecipeIngredient> searchRecipeIngredients = recipeIngredientRepository.findRecipeIngredientsByRecipeId(recipeId);
        if (searchRecipeIngredients.size()==0) throw new RecipeNotFoundException("recipeId="+recipeId+"인 재료가 없습니다.");

        RecipeIngredientListRes recipeIngredientListRes = new RecipeIngredientListRes(recipeId);

		List<RecipeIngredientInfo> recipeIngredientInfoList = recipeIngredientListRes.getRecipeIngredientInfoList();

		for (RecipeIngredientInfo info : recipeIngredientInfoList) {

			List<RecipeIngredient> recipeIngredientList = info.getRecipeIngredientList();

            for (com.ssafy.ingredientservice.db.entity.RecipeIngredient recipeIngredient : searchRecipeIngredients){
                Optional<Ingredient> searchIngredient = ingredientRepository.findIngredientByIngredientId(recipeIngredient.getIngredientId());
                if (!searchIngredient.isPresent()) throw new IngredientNotFoundException("IngredientId="+recipeIngredient.getIngredientId()+"인 재료가 없습니다.");
                Ingredient ingredient = searchIngredient.get();
                switch (info.getRecipeIngredientType()) {
                    case FRUIT -> {
                        if (ingredient.getIngredientTypeId()==0) {
                            recipeIngredientList.add(
                                    RecipeIngredient.builder()
                                            .recipeIngredientId(recipeIngredient.getRecipeIngredientId())
                                            .recipeIngredientName(recipeIngredient.getRecipeIngredientName())
                                            .recipeIngredientVolume(recipeIngredient.getRecipeIngredientVolume())
                                            .build()
                            );
                        }
                    }
                    case VEGETABLE -> {
                        if (ingredient.getIngredientTypeId()==1) {
                            recipeIngredientList.add(
                                    RecipeIngredient.builder()
                                            .recipeIngredientId(recipeIngredient.getRecipeIngredientId())
                                            .recipeIngredientName(recipeIngredient.getRecipeIngredientName())
                                            .recipeIngredientVolume(recipeIngredient.getRecipeIngredientVolume())
                                            .build()
                            );
                        }
                    }
                    case RICE_GRAIN -> {
                        if (ingredient.getIngredientTypeId()==2) {
                            recipeIngredientList.add(
                                    RecipeIngredient.builder()
                                            .recipeIngredientId(recipeIngredient.getRecipeIngredientId())
                                            .recipeIngredientName(recipeIngredient.getRecipeIngredientName())
                                            .recipeIngredientVolume(recipeIngredient.getRecipeIngredientVolume())
                                            .build()
                            );
                        }
                    }
                    case MEAT_EGG -> {
                        if (ingredient.getIngredientTypeId()==3) {
                            recipeIngredientList.add(
                                    RecipeIngredient.builder()
                                            .recipeIngredientId(recipeIngredient.getRecipeIngredientId())
                                            .recipeIngredientName(recipeIngredient.getRecipeIngredientName())
                                            .recipeIngredientVolume(recipeIngredient.getRecipeIngredientVolume())
                                            .build()
                            );
                        }
                    }
                    case FISH -> {
                        if (ingredient.getIngredientTypeId()==4) {
                            recipeIngredientList.add(
                                    RecipeIngredient.builder()
                                            .recipeIngredientId(recipeIngredient.getRecipeIngredientId())
                                            .recipeIngredientName(recipeIngredient.getRecipeIngredientName())
                                            .recipeIngredientVolume(recipeIngredient.getRecipeIngredientVolume())
                                            .build()
                            );
                        }
                    }
                    case MILK -> {
                        if (ingredient.getIngredientTypeId()==5) {
                            recipeIngredientList.add(
                                    RecipeIngredient.builder()
                                            .recipeIngredientId(recipeIngredient.getRecipeIngredientId())
                                            .recipeIngredientName(recipeIngredient.getRecipeIngredientName())
                                            .recipeIngredientVolume(recipeIngredient.getRecipeIngredientVolume())
                                            .build()
                            );
                        }
                    }
                    case SAUCE -> {
                        if (ingredient.getIngredientTypeId()==6) {
                            recipeIngredientList.add(
                                    RecipeIngredient.builder()
                                            .recipeIngredientId(recipeIngredient.getRecipeIngredientId())
                                            .recipeIngredientName(recipeIngredient.getRecipeIngredientName())
                                            .recipeIngredientVolume(recipeIngredient.getRecipeIngredientVolume())
                                            .build()
                            );
                        }
                    }
                    case ETC -> {
                        if (ingredient.getIngredientTypeId()==7) {
                            recipeIngredientList.add(
                                    RecipeIngredient.builder()
                                            .recipeIngredientId(recipeIngredient.getRecipeIngredientId())
                                            .recipeIngredientName(recipeIngredient.getRecipeIngredientName())
                                            .recipeIngredientVolume(recipeIngredient.getRecipeIngredientVolume())
                                            .build()
                            );
                        }
                    }
                    default -> {
                        return responseService.INTERNAL_SERVER_ERROR();
                    }
                }
            }
        }
        return ResponseEntity.ok()
                .body(responseService.getSuccessSingleResult(recipeIngredientListRes, "레시피 재료 조회 성공"));
    }

    public ResponseEntity<?> getIngredientList(IngredientListReq req) throws IngredientNotFoundException {
        List<Integer> ingredientIdList = req.getIngredientIdList();
        List<Ingredient> IngredientList = new ArrayList<>();
        for (Integer ingredientId : ingredientIdList) {
            Optional<Ingredient> searchIngredient = ingredientRepository.findIngredientByIngredientId(ingredientId);
            if(!searchIngredient.isPresent()) throw new IngredientNotFoundException("ingredientId="+ingredientId+"인 재료가 없습니다.");
            Ingredient ingredient = searchIngredient.get();
            IngredientList.add(ingredient);
        }

        IngredientListRes ingredientListRes = new IngredientListRes();

        List<IngredientInfo> ingredientInfoList = ingredientListRes.getIngredientInfoList();

        for (IngredientInfo info : ingredientInfoList) {

            List<IngredientRes> ingredientResList = info.getIngredientResList();

            for (Ingredient ingredient : IngredientList){
                switch (info.getIngredientType()) {
                    case FRUIT -> {
                        if (ingredient.getIngredientTypeId()==0) {
                            ingredientResList.add(
                                    IngredientRes.builder()
                                            .ingredientId(ingredient.getIngredientId())
                                            .ingredientName(ingredient.getIngredientName())
                                            .build()
                            );
                        }
                    }
                    case VEGETABLE -> {
                        if (ingredient.getIngredientTypeId()==1) {
                            ingredientResList.add(
                                    IngredientRes.builder()
                                            .ingredientId(ingredient.getIngredientId())
                                            .ingredientName(ingredient.getIngredientName())
                                            .build()
                            );
                        }
                    }
                    case RICE_GRAIN -> {
                        if (ingredient.getIngredientTypeId()==2) {
                            ingredientResList.add(
                                    IngredientRes.builder()
                                            .ingredientId(ingredient.getIngredientId())
                                            .ingredientName(ingredient.getIngredientName())
                                            .build()
                            );
                        }
                    }
                    case MEAT_EGG -> {
                        if (ingredient.getIngredientTypeId()==3) {
                            ingredientResList.add(
                                    IngredientRes.builder()
                                            .ingredientId(ingredient.getIngredientId())
                                            .ingredientName(ingredient.getIngredientName())
                                            .build()
                            );
                        }
                    }
                    case FISH -> {
                        if (ingredient.getIngredientTypeId()==4) {
                            ingredientResList.add(
                                    IngredientRes.builder()
                                            .ingredientId(ingredient.getIngredientId())
                                            .ingredientName(ingredient.getIngredientName())
                                            .build()
                            );
                        }
                    }
                    case MILK -> {
                        if (ingredient.getIngredientTypeId()==5) {
                            ingredientResList.add(
                                    IngredientRes.builder()
                                            .ingredientId(ingredient.getIngredientId())
                                            .ingredientName(ingredient.getIngredientName())
                                            .build()
                            );
                        }
                    }
                    case SAUCE -> {
                        if (ingredient.getIngredientTypeId()==6) {
                            ingredientResList.add(
                                    IngredientRes.builder()
                                            .ingredientId(ingredient.getIngredientId())
                                            .ingredientName(ingredient.getIngredientName())
                                            .build()
                            );
                        }
                    }
                    case ETC -> {
                        if (ingredient.getIngredientTypeId()==7) {
                            ingredientResList.add(
                                    IngredientRes.builder()
                                            .ingredientId(ingredient.getIngredientId())
                                            .ingredientName(ingredient.getIngredientName())
                                            .build()
                            );
                        }
                    }
                    default -> {
                        return responseService.INTERNAL_SERVER_ERROR();
                    }
                }
            }
        }
        return ResponseEntity.ok()
                .body(responseService.getSuccessSingleResult(ingredientListRes, "재료 조회 성공"));
    }


    public ResponseEntity<?> naverReceiptIntoNames(@RequestBody ConvertImageReq req){
        return null;
    }



    // 조리를 위해 부족한 재료 채우기
    // 백엔드에서 recipe_ingredient → ingredient → fridge 확인 필요
    // 냉장고에 부족한 재료 추가하기
    public boolean getIngredientIdToCook(String userSnsId, String token, String recipeId) {


        // recipeClient 통신해서 필요한 ingredientId 정보 가져오기
        ResponseEntity<?> res = recipeServiceClient.getRecipeIngredients(recipeId, token);
        // res.getBody().getData();
        // fridgeClient 통신해서 유저가 가지고 있는 ingredientId 정보 가져오기
        // recipe 에 있으면서 fridge 에 없는 재료 도출하고 반환해주기
        // fridgeClient 통신해서 냉장고에 부족한 재료 추가하기

        RecipeIngredientListRes recipeIngredientListRes = new RecipeIngredientListRes(1);

        List<RecipeIngredientInfo> recipeIngredientInfoList = recipeIngredientListRes.getRecipeIngredientInfoList();


        for (RecipeIngredientInfo info : recipeIngredientInfoList) {

            List<RecipeIngredient> recipeIngredientList = info.getRecipeIngredientList();

            switch (info.getRecipeIngredientType()) {

                case FRUIT -> {
                    recipeIngredientList.add(
                        RecipeIngredient.builder()
                            .recipeIngredientId(10)
                            .recipeIngredientName("사과")
                            .recipeIngredientVolume("1쪽")
                            .build()
                    );
                }
                case VEGETABLE -> {
                    recipeIngredientList.add(
                        RecipeIngredient.builder()
                            .recipeIngredientId(11)
                            .recipeIngredientName("대파")
                            .recipeIngredientVolume("1망")
                            .build()
                    );
                }
                case RICE_GRAIN -> {
                    recipeIngredientList.add(
                        RecipeIngredient.builder()
                            .recipeIngredientId(13)
                            .recipeIngredientName("햅쌀")
                            .recipeIngredientVolume("1큰술")
                            .build()
                    );
                }
                case MEAT_EGG -> {
                    recipeIngredientList.add(
                        RecipeIngredient.builder()
                            .recipeIngredientId(14)
                            .recipeIngredientName("달걀")
                            .recipeIngredientVolume("흰자")
                            .build()
                    );
                }
                case FISH -> {
                    recipeIngredientList.add(
                        RecipeIngredient.builder()
                            .recipeIngredientId(15)
                            .recipeIngredientName("고등어")
                            .recipeIngredientVolume("1마리")
                            .build()
                    );
                }
                case MILK -> {
                    recipeIngredientList.add(
                        RecipeIngredient.builder()
                            .recipeIngredientId(16)
                            .recipeIngredientName("체다치즈")
                            .recipeIngredientVolume("1장")
                            .build()
                    );
                }
                case SAUCE -> {
                    recipeIngredientList.add(
                        RecipeIngredient.builder()
                            .recipeIngredientId(17)
                            .recipeIngredientName("고추장")
                            .recipeIngredientVolume("1큰술")
                            .build()
                    );
                }
                case ETC -> {
                    recipeIngredientList.add(
                        RecipeIngredient.builder()
                            .recipeIngredientId(18)
                            .recipeIngredientName("제육볶음")
                            .recipeIngredientVolume("1팩")
                            .build()
                    );
                }


            }
        }
        return true;
    }

}
