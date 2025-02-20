package com.project.zipmin.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.member.LikeDTO;
import com.project.zipmin.member.MemberDTO;

@RestController
public class RecipeController {
	
	@GetMapping("/recipe")
	public List<Map<String, Object>> listRecipe() {
		List<Map<String, Object>> recipeList = new ArrayList<>();
		
		// 첫번째 더미데이터
		Map<String, Object> recipe = new HashMap<>();
		recipe.put("title", "김치볶음밥");
		recipe.put("level", "중급");
		recipe.put("time", "10분");
		recipe.put("spicy", "아주 매움");
		recipe.put("serving", "3인분");
		recipe.put("writerRef", "harim");
		recipeList.add(recipe);
		
		return recipeList;
	}
	
	@GetMapping("/recipe/{recipeIdx}")
	@ResponseBody
	public RecipeDTO viewRecipe(@PathVariable("recipeIdx") Integer recipeIdx) {
		
		// recipeIdx 보내서 레시피 받아오기 (실제로는 매퍼 호출)
		RecipeDTO recipeDTO = new RecipeDTO();
		// 더미데이터
		recipeDTO.setTitle("김치볶음밥");
		recipeDTO.setLevel("쉬움");
		recipeDTO.setTime("15분");
		recipeDTO.setSpicy("아주 매움");
		recipeDTO.setMember_ref("harim");
		recipeDTO.setIntroduce("이 요리는 영국에서 최초로 시작되어 일년에 한바퀴를 돌면서 받는 사람에게 행운을 주었고"
				+ "	지금은 당신에게로 옮겨진 이 요리는 4일 안에 당신 곁을 떠나야 합니다."
				+ "	이 요리를 포함해서 7통을 행운이 필요한 사람에게 보내 주셔야 합니다."
				+ "	복사를 해도 좋습니다. 혹 미신이라 하실지 모르겠지만 사실입니다.");
		recipeDTO.setServing(2);
		recipeDTO.setTip("이틀 안에 모두 드세요");
		
		// reviewIdx 보내서 해당하는 category 읽어오기
		CategoryDTO categoryDTO = new CategoryDTO();
		// 더미데이터
		categoryDTO.setType("밥/죽/떡");
		categoryDTO.setSituation("일상");
		categoryDTO.setIngredient("김치");
		categoryDTO.setWay("볶음");
		categoryDTO.setRecipe_ref(1);
		recipeDTO.setCategory(categoryDTO);
		
		// recipeIdx 보내서 해당하는 member 닉네임 가져오기
		MemberDTO memberDTO = new MemberDTO();
		// 더미데이터
		memberDTO.setNickname("뽀야미가 되고 싶은 아잠만");
		recipeDTO.setMember(memberDTO);
		
		// recipeIdx랑 login user도 보내서 레시피에 좋아요누른 여부 가져와야 함
		// recipeIdx랑 login user도 보내서 신고 누른 여부도 마찬가지
		// 가져와서 아이콘 처리하기
		
		// 재료 recipeIdx 보내서 해당하는 재료 목록 가져오기
		List<IngredientDTO> ingredientList = new ArrayList<IngredientDTO>();
		// 더미데이터
		IngredientDTO ingredientDTO1 = new IngredientDTO();
		ingredientDTO1.setIngredient_idx(1);
		ingredientDTO1.setName("마늘");
		ingredientDTO1.setAmount(5);
		ingredientDTO1.setUnit("쪽");
		ingredientDTO1.setNote("슬라이스");
		ingredientDTO1.setRecipe_ref(1);
		ingredientList.add(ingredientDTO1);
		IngredientDTO ingredientDTO2 = new IngredientDTO();
		ingredientDTO2.setIngredient_idx(2);
		ingredientDTO2.setName("김치");
		ingredientDTO2.setAmount(2);
		ingredientDTO2.setUnit("포기");
		ingredientDTO2.setRecipe_ref(1);
		ingredientList.add(ingredientDTO2);
		recipeDTO.setIngredient_list(ingredientList);
		
		// 조리 순서 recipeIdx 보내서 해당하는 조리 과정 index 순서대로 가져오기
		List<StepDTO> stepList = new ArrayList<StepDTO>();
		// 더미데이터
		StepDTO stepDTO1 = new StepDTO();
		stepDTO1.setStep_idx(1);
		stepDTO1.setDescription("1번 설명입니다");
		stepDTO1.setRecipe_ref(1);
		stepList.add(stepDTO1);
		StepDTO stepDTO2 = new StepDTO();
		stepDTO2.setStep_idx(2);
		stepDTO2.setDescription("2번 설명입니다");
		stepDTO2.setRecipe_ref(1);
		stepList.add(stepDTO2);
		recipeDTO.setStep_list(stepList);
		
		// memberidx랑 login user 보내서 팔로우 여부 팔로우 수 등등
		recipeDTO.setFollower_count(45);
		recipeDTO.setIsFollow(true);
		
		recipeDTO.setReview_count(143);
		recipeDTO.setComment_count(123);
		
		return recipeDTO;
	}
	
	
	

}
