package vip.malagu.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public final class ColorIconUtils {
	
	private ColorIconUtils() {}

	public static List<String> getColorsBy(Integer number) {
		
		String[] colors = {"fb0223", "6199bc", "940c4b", "083cb2",
				"0b7973", "0f2153", "0f4b9b", "0f5bc2", "0f72f6",
				"1195db", "1258f8", "1296db", "1aaba8", "1afa29",
				"1b3b98", "2aa515", "32cacd", "36ab60", "39579c",
				"3f81c1", "4b13f7", "4f68b0", "515151", "562674",
				"5a068a", "5db539", "0061b2", "630b9b", "66a0ee",
				"6d1004", "71bc22", "7cba59", "7dc5eb", "7dee09",
				"82529d", "83c6c2", "8992c8", "8a0dd8", "8a8a8a",
				"8c0cd4", "8cbb19", "8e732c", "06f6de", "969d2f",
				"a686ba", "ab1a07", "ad1bff", "b34ddd", "b651f4",
				"bc8c09", "bdd9b1", "c17be9", "c5acd3", "c81f6d",
				"c8db8c", "cc6f63", "d7e50e", "d81e06", "e16531",
				"e89abe", "eeb173", "f31179", "f3b206", "f4d174",
				"f65da3", "f9f28b", "000000"};
		
		List<String> result = new ArrayList<>();
		if (number > colors.length) {
			result.addAll(Arrays.asList(colors));
			for (int i = 0; i < (number - colors.length); i++) {
				result.add(colors[i]);
			}
		} else {
			Random random = new Random();
			Set<Integer> nums = new HashSet<>();
			for (int i = 0; i < 1000; i++) {
				int index = random.nextInt(65);
				nums.add(index);
				if (nums.size() == number) {
					break;
				}
			}
			List<Integer> ns = new ArrayList<>(nums);
			for (int i = 0; i < ns.size(); i++) {
				result.add(colors[ns.get(i)]);
			}
			
		}
		return result;
	}

}
