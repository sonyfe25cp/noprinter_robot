package com.omartech;

import java.util.Random;

public class Test {

	public static void main(String[] args) {
		Test t =new Test();
		for(int i = 0;  i < 100; i ++){
			t.run();
		}
	}


	void run(){
		String[] array = { "【NoPrinter打印店】论文打印我们更专业！！！", "放假不回家的童鞋，寄些照片回家吧~~",
				"电子书打印哪家强？北理菜市场NoPrinter打印店啊~~", "顶贴送代金券啦~~打印店赔本赚吆喝啦~~~" };

		Random random = new Random();
		float nextDouble = random.nextFloat();
		float a = nextDouble * (array.length - 1);
		int round = Math.round(a);
		String titleBak = array[round];
		System.out.println(round);
	}
}
