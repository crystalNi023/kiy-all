package com.kiy.test;

public class TestWriteAndReadCard {
	public static void main(String[] args) {
//		Executor.initialize();
//		CardSerialPort cardSerialPort = new CardSerialPort();
//		cardSerialPort.start();
//		Card card = new Card();
//		card.setCardNumber("00000011");
////		card.setHour(24);
//		card.setNumber(5);
//		card.setType(Card.NUMBER_TYPE);
////		cardSerialPort.writeCard(card);
//		
//		
//		Card card1 = new Card();
//		cardSerialPort.readCard(card1);
//		System.out.println("卡号 :"+card1.getCardNumber());
//		System.out.println("类型 :"+card1.getType());
//		System.out.println("次数 :"+card1.getNumber());
//		System.out.println("时间:"+card1.getHour());
//		System.out.println("刷卡时间:"+card1.getDate());
		
		System.out.println("卡号为 = " + 16);
		String str = String.format("%08d", 16); 
		System.out.println(str);
		
		Integer valueOf2 = Integer.valueOf(str);
		System.out.println(valueOf2);
		
		
	}
	
}
