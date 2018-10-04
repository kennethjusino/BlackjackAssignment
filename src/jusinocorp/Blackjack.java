package jusinocorp;

import java.util.Scanner;

public class Blackjack {

	public static void main(String[] args) {
		System.out.println("Welcome to Blackjack!");
		System.out.println("If you wish to quit playing, remember to bet $0 ");
		//Create our playing deck
		Deck playingDeck = new Deck();
		playingDeck.createFullDeck();
		playingDeck.shuffle();
		
		//Create a deck for the player
		Deck playerDeck = new Deck();
		Deck dealerDeck = new Deck();
		
		double playerMoney = 100.00 ;
		
		
		Scanner userInput = new Scanner(System.in);
		
		//Game loop
		while(playerMoney > 0) {
			System.out.println("You have $"+ playerMoney + " left, how much do you want to bet?");
			double playerBet = userInput.nextDouble();
			if(playerBet == 0) {
				System.out.println("Sad to see you go!");
				break;
			}
			if(playerBet > playerMoney) {
				System.out.println("You cant bet what you dont have! GET OUT MY CASINO!");
				break;
			}
			
			boolean endRound = false;
			boolean doubledUp = false;
			boolean splitAvailable = false;
			
			//start dealing in alternating sequence
			playerDeck.draw(playingDeck);
			dealerDeck.draw(playingDeck);
			playerDeck.draw(playingDeck);
			dealerDeck.draw(playingDeck);
			
			//checks for split availability
			if(playerDeck.getCard(0).toString() == playerDeck.getCard(1).toString()) {
				splitAvailable = true;
			}
			
			//repeats as player plays
			while(true) {
				//displaying player hand
				System.out.println("Your hand:");
				System.out.println(playerDeck.toString());
				System.out.println("Your hand is valued at: " + playerDeck.cardsValue());
				
				
				//Displaying dealer hand
				System.out.println("Dealer Hand: " + dealerDeck.getCard(0).toString() + " and [Hidden]");
				
				//player decision
				if(splitAvailable == false) {
					System.out.println("What would you like to do: [1] Hit, [2] Stand, [3] Double-Up?");
				}
				else if(splitAvailable == true){
					System.out.println("What would you like to do: [1] Hit, [2] Stand, [3] Double-Up, [4] Split?");
				}
				
				
				int decision = userInput.nextInt();
				
				//if player chooses to hit
				if(decision == 1) {
					playerDeck.draw(playingDeck);
					System.out.println("You drew a " + playerDeck.getCard(playerDeck.deckSize()-1).toString());
					
					if(playerDeck.cardsValue() > 21) {
						System.out.println("You Bust: Currently valued at "+ playerDeck.cardsValue());
						playerMoney -= playerBet;
						endRound = true;
						break;
					}
					//if player busts *
					
				}
				if(decision == 2) {
					break;
				}
				if(decision == 3) {
					if((playerMoney - (2.0*playerBet)) < 0) {
						System.out.println("You cant double up what you dont have! You miss your turn");
						endRound = true;
						break;
					}
					else {
						doubledUp = true;
						playerDeck.draw(playingDeck);
						System.out.println("You drew a " + playerDeck.getCard(playerDeck.deckSize()-1).toString());
						
						if(playerDeck.cardsValue() > 21) {
							System.out.println("You Bust: Currently valued at "+ playerDeck.cardsValue());
							playerMoney -= (playerBet * 2.0);
							endRound = true;
							break;
						}
						
						else {
							System.out.println("Your hand:");
							System.out.println(playerDeck.toString());
							System.out.println("Your hand is valued at: " + playerDeck.cardsValue());
							break;
						}
					}
				}
				if(decision == 4) {
					if(splitAvailable == true) {
						Deck playerDeck2 = new Deck();
						playerDeck2.draw(playerDeck);
					}
					else if (splitAvailable == false) {
						System.out.println("Sorry, you cant split.");
					}
				}
			}
			if(doubledUp == true) {
				playerBet += playerBet;
				
			}
			
			
			//After player stands
			System.out.println("Dealer cards: " + dealerDeck.toString());
			
			if((dealerDeck.cardsValue() > playerDeck.cardsValue()) && endRound == false){
				System.out.println("Dealer has defeated you!");
				playerMoney -= playerBet;
				endRound = true;
				
			}
			//dealer draws until above 17
			while((dealerDeck.cardsValue()< 17) && endRound == false) {
				dealerDeck.draw(playingDeck);
				System.out.println("Dealer Draws: " + dealerDeck.getCard(dealerDeck.deckSize()-1).toString());
				
			}
			
			
			//if dealer busts
			System.out.println("Dealers deck is valued at: "+ dealerDeck.cardsValue());
			if((dealerDeck.cardsValue() > 21) && endRound == false){
				System.out.println("Dealer busts. You have won.");
				playerMoney += playerBet;
				endRound = true;
			}
			
			
			//if its a push
			if((playerDeck.cardsValue() == dealerDeck.cardsValue()) && endRound == false) {
				System.out.println("Push");
				endRound = true;
				
			}
			
			//if player is closer to 21
			if((playerDeck.cardsValue() > dealerDeck.cardsValue()) && endRound == false) {
				System.out.println("You win the hand!");
				playerMoney += playerBet;
				endRound = true;
				
			}
			
			//if dealer wins
			else if(endRound == false) {
				System.out.println("You have lost!");
				playerMoney -= playerBet;
				endRound = true;
			}
			
			playerDeck.moveAllToDeck(playingDeck);
			dealerDeck.moveAllToDeck(playingDeck);
			System.out.println("End of Hand.");
		}
		if(playerMoney == 0) {
			System.out.println("You ran out of money!");
		}
		System.out.println("Thanks for playing! Goodbye!");
		userInput.close();
	}
}
