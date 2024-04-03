package com.ssafy.ingredientservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ingredientservice.db.entity.RecipeIngredient;
import com.ssafy.ingredientservice.db.entity.Ingredient;
import com.ssafy.ingredientservice.db.entity.IngredientOCR;
import com.ssafy.ingredientservice.db.entity.OCRResult;
import com.ssafy.ingredientservice.db.entity.client.ClientIngredientIdListRes;
import com.ssafy.ingredientservice.db.entity.client.ClientIngredientListReq;
import com.ssafy.ingredientservice.db.repository.IngredientOCRRepository;
import com.ssafy.ingredientservice.db.repository.IngredientRepository;
import com.ssafy.ingredientservice.db.repository.RecipeIngredientRepository;
import com.ssafy.ingredientservice.dto.request.ConvertImageReq;
import com.ssafy.ingredientservice.dto.request.IngredientListReq;
import com.ssafy.ingredientservice.dto.response.ConvertProduct;
import com.ssafy.ingredientservice.dto.response.ConvertProductInfo;
import com.ssafy.ingredientservice.dto.response.ConvertProductListRes;
import com.ssafy.ingredientservice.dto.response.IngredientId;
import com.ssafy.ingredientservice.dto.response.RecipeIngredientInfo;
import com.ssafy.ingredientservice.dto.response.IngredientInfo;
import com.ssafy.ingredientservice.dto.response.RecipeIngredientDTO;
import com.ssafy.ingredientservice.dto.response.IngredientRes;
import com.ssafy.ingredientservice.dto.response.RecipeIngredientListRes;
import com.ssafy.ingredientservice.dto.response.IngredientListRes;
import com.ssafy.ingredientservice.exception.exception.BaseException;
import com.ssafy.ingredientservice.exception.exception.HaveAllIngredientInRecipeException;
import com.ssafy.ingredientservice.exception.exception.IngredientNotFoundException;
import com.ssafy.ingredientservice.exception.exception.NoContentException;
import com.ssafy.ingredientservice.exception.exception.RecipeNotFoundException;
import com.ssafy.ingredientservice.service.client.FridgeServiceClient;
import com.ssafy.ingredientservice.service.client.RecipeServiceClient;

import lombok.AllArgsConstructor;

import com.ssafy.ingredientservice.util.result.SingleResult;
import com.ssafy.ingredientservice.util.sungchefEnum.ConvertIngredientType;
import com.ssafy.ingredientservice.util.sungchefEnum.IngredientType;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class IngredientService {

    private final ResponseService responseService;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeServiceClient recipeServiceClient;
    private final IngredientOCRRepository ingredientOCRRepository;
    private final OCRApiService ocrApiService;
    private final FridgeServiceClient fridgeServiceClient;

    public ResponseEntity<?> getUsedIngredientsInRecipe(Integer recipeId) throws IngredientNotFoundException, RecipeNotFoundException {
        List<RecipeIngredient> searchRecipeIngredients = recipeIngredientRepository.findRecipeIngredientsByRecipeId(recipeId);
        if (searchRecipeIngredients.size()==0) throw new RecipeNotFoundException("recipeId="+recipeId+"인 재료가 없습니다.");

        RecipeIngredientListRes recipeIngredientListRes = new RecipeIngredientListRes(recipeId);

		List<RecipeIngredientInfo> recipeIngredientInfoList = recipeIngredientListRes.getRecipeIngredientInfoList();



		for (RecipeIngredientInfo info : recipeIngredientInfoList) {

			List<RecipeIngredientDTO> recipeIngredientList = info.getRecipeIngredientList();

            for (RecipeIngredient recipeIngredient : searchRecipeIngredients){
                Optional<Ingredient> searchIngredient = ingredientRepository.findIngredientByIngredientId(recipeIngredient.getIngredientId());
                if (!searchIngredient.isPresent()) throw new IngredientNotFoundException("IngredientId="+recipeIngredient.getIngredientId()+"인 재료가 없습니다.");
                Ingredient ingredient = searchIngredient.get();
                switch (info.getRecipeIngredientType()) {
                    case FRUIT -> {
                        if (ingredient.getIngredientTypeId()==0) {
                            recipeIngredientList.add(
                                RecipeIngredientDTO.builder()
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
                                RecipeIngredientDTO.builder()
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
                                RecipeIngredientDTO.builder()
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
                                RecipeIngredientDTO.builder()
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
                                RecipeIngredientDTO.builder()
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
                                RecipeIngredientDTO.builder()
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
                                RecipeIngredientDTO.builder()
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
                                RecipeIngredientDTO.builder()
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

    public ResponseEntity<SingleResult<IngredientListRes>> getIngredientList(IngredientListReq req) throws IngredientNotFoundException {
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
                        throw new BaseException("Parse Error");
                    }
                }
            }
        }
        return ResponseEntity.ok()
                .body(responseService.getSuccessSingleResult(ingredientListRes, "재료 조회 성공"));
    }

    public String setOCRData(MultipartFile file) {
        OCRResult ocrResult = convertOCR(file);
        return insertOCR(ocrResult);
    }

    public OCRResult convertOCR(MultipartFile file) {
        return ocrApiService.convertOCR(file);
    }

    @Transactional
    public String insertOCR(OCRResult ocrResult) {
        String uuid = UUID.randomUUID().toString();

        List<IngredientOCR> convertIngredient = ocrResult
            .images()
            .get(0)
            .receipt()
            .result()
            .subResults()
            .get(0).items().stream()
            .map(
                item -> IngredientOCR.builder()
                    .ingredientOCRId(-1)
                    .ingredientOCRUUID(uuid)
                    .ingredientOCRText(item.name().text())
                    .build()
            )
            .toList();
        ingredientOCRRepository.saveAll(convertIngredient);
        return uuid;
    }

    @Transactional
    public ConvertProductListRes findConvertImage(String uuid) {

        List<IngredientOCR> ingredientOCRList = ingredientOCRRepository.findAllByIngredientOCRUUID(uuid);
        List<String> ocrTestList = ingredientOCRList
            .stream().map(IngredientOCR::getIngredientOCRText).toList();

        ConvertProductListRes res = new ConvertProductListRes();

        List<ConvertProductInfo> convertProductInfoList = res.getConvertProductList();

        for (String ocrText : ocrTestList) {
            Optional<Ingredient> selectIngredient = ingredientRepository.findIngredientByOCR(ocrText);
            if (selectIngredient.isEmpty()) {
                convertProductInfoList
                    .get(ConvertIngredientType.NOT_CONVERTED.getCode())
                    .getConvertProductList().add(ConvertProduct
                        .builder()
                        .ingredientId(-1)
                        .convertedName(ocrText)
                        .isConverted(false)
                        .build());
            } else {
                Ingredient ingredient = selectIngredient.get();
                convertProductInfoList
                    .get(ingredient.getIngredientTypeId())
                    .getConvertProductList().add(ConvertProduct
                        .builder()
                        .ingredientId(ingredient.getIngredientId())
                        .convertedName(ingredient.getIngredientName())
                        .isConverted(true)
                        .build());
            }
        }
        return res;
    }


    public ResponseEntity<?> naverReceiptIntoNames(@RequestBody ConvertImageReq req){
        return null;
    }


    /* getIngredientIdToCook : 냉장고에서 부족한 재료 조회하기
    * @param : String recipeId
    * @return : RecipeIngredientListRes (레시피id, 필요한 재료 정보 (재료 타입, (재료id, 재료이름, 재료양 )))
    * */
    public RecipeIngredientListRes getIngredientIdToCook(String userSnsId, String token, String recipeIdStr) {

        Integer recipeId = Integer.valueOf(recipeIdStr);

        // DB에서 필요한 ingredientId 정보 가져오기
        List<RecipeIngredient> totalRecipeIngredients = recipeIngredientRepository.findRecipeIngredientsByRecipeId(recipeId);
        IngredientListReq isExistReq = new IngredientListReq();
        List<Integer> ingredientIdList = new ArrayList<>();
        for (RecipeIngredient totalRecipeIngredient : totalRecipeIngredients) {
            Integer ingredientId = (Integer) totalRecipeIngredient.getIngredientId();
            ingredientIdList.add(ingredientId);
        }
        isExistReq.setIngredientIdList(ingredientIdList);

        // fridgeClient 통신해서 부족한 ingredientId 정보 가져오기
        ResponseEntity<ClientIngredientIdListRes> resFridge = null;
        try {
            resFridge = fridgeServiceClient.getFridgeIngredients(token, isExistReq);
        } catch (Exception e) {
            throw new NoContentException("냉장고에 모든 재료가 존재함");
        }
        // ResponseEntity<ClientIngredientIdListRes> resFridge = fridgeServiceClient.getFridgeIngredients(token, isExistReq);
        // log.info("resFridge:{}",resFridge);
        // 반환할 응답 만들기 (response)
        // 1. recipeIngredient table 에서 조회해 올 ingredientIdList 얻기
        // String ingredientIdListString = resFridge.getBody().toString();
        // ObjectMapper lackingIngredientIdListParser = new ObjectMapper();
        // IngredientListReq ingredientListReq = lackingIngredientIdListParser.readValue(ingredientIdListString,
        //     IngredientListReq.class);
        List<Integer> ingredientIdReqList = resFridge.getBody().ingredientIdList().stream().toList();
        IngredientListReq ingredientListReq = new IngredientListReq();
        ingredientListReq.setIngredientIdList(ingredientIdReqList);
        // 2. 재료 id List 로 재료 상세 정보 얻어오기
        ResponseEntity<SingleResult<IngredientListRes>> resIngredientDetail = getIngredientList(ingredientListReq);

        // 3. 가져온 상세 정보 parsing 하기 (JSON -> IngredientListRes)
        // String ingredientInfoListString = resIngredientDetail.getBody().toString();
        // ObjectMapper ingredientInfoListParser = new ObjectMapper();
        IngredientListRes ingredientInfoList = resIngredientDetail.getBody().getData();

        // 4. 응답 객체 생성하기
        RecipeIngredientListRes res = new RecipeIngredientListRes(recipeId); // 최종 반환할 응답 객체 형태

        List<RecipeIngredient> allRecipeIngredients = recipeIngredientRepository.findRecipeIngredientsByRecipeId(recipeId);

        for (IngredientInfo ingredientInfo : ingredientInfoList.getIngredientInfoList()) {
            IngredientType ingredientType = ingredientInfo.getIngredientType();

            for (RecipeIngredientInfo recipeIngredientInfo : res.getRecipeIngredientInfoList()) {
                if (recipeIngredientInfo.getRecipeIngredientType().equals(ingredientType)) {
                    for (IngredientRes ingredientRes : ingredientInfo.getIngredientResList()) {
                        String volume = "양 없음"; // 기본값 설정
                        // 이제 for 문을 사용하여 recipeIngredientVolume을 찾습니다.
                        for (RecipeIngredient recipeIngredient : allRecipeIngredients) {
                            if (recipeIngredient.getIngredientId() == ingredientRes.getIngredientId()) {
                                volume = recipeIngredient.getRecipeIngredientVolume();
                                break; // 일치하는 첫 번째 레코드를 찾으면 반복을 중단합니다.
                            }
                        }

                        RecipeIngredientDTO recipeIngredientDTO = RecipeIngredientDTO.builder()
                            .recipeIngredientId(ingredientRes.getIngredientId())
                            .recipeIngredientName(ingredientRes.getIngredientName())
                            .recipeIngredientVolume(volume)
                            .build();
                        recipeIngredientInfo.getRecipeIngredientList().add(recipeIngredientDTO);
                    }
                    break; // 해당 타입에 대한 정보를 모두 추가했으므로 다음 타입으로 넘어갑니다.
                }
            }
        }
        // 최종적으로 구성된 RecipeIngredientListRes 객체를 반환합니다.
        return res;
    }

    public ResponseEntity<?> addIngredientIdToCook(String userSnsId, String token, String recipeIdStr) {
        Integer recipeId = Integer.valueOf(recipeIdStr);

        // DB에서 필요한 ingredientId 정보 가져오기
        List<RecipeIngredient> totalRecipeIngredients = recipeIngredientRepository.findRecipeIngredientsByRecipeId(recipeId);
        IngredientListReq isExistReq = new IngredientListReq();
        List<Integer> ingredientIdList = new ArrayList<>();
        for (RecipeIngredient totalRecipeIngredient : totalRecipeIngredients) {
            Integer ingredientId = (Integer) totalRecipeIngredient.getIngredientId();
            ingredientIdList.add(ingredientId);
        }
        isExistReq.setIngredientIdList(ingredientIdList);

        // fridgeClient 통신해서 부족한 ingredientId 정보 가져오기
        ResponseEntity<ClientIngredientIdListRes> resFridge = null;
        try {
            resFridge = fridgeServiceClient.getFridgeIngredients(token, isExistReq);
            if (resFridge == null) throw new HaveAllIngredientInRecipeException("냉장고에 모든 재료가 존재함");
        } catch (Exception e) {
            throw new HaveAllIngredientInRecipeException("냉장고에 모든 재료가 존재함");
        }

        List<IngredientId> reqIngredientIdList = resFridge.getBody()
            .ingredientIdList()
            .stream().map(
                integer -> IngredientId.builder()
                    .ingredientId(integer)
                    .build()
            ).toList();
        ClientIngredientListReq req = ClientIngredientListReq.builder().ingredientIdList(reqIngredientIdList).build();
        try {
            ResponseEntity<?> result = fridgeServiceClient.addIngredients(token, req);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(responseService.getSuccessMessageResult("부족한 재료 목록 조회 성공"));
    }
}
