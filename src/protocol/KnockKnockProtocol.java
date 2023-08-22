package protocol;

public class KnockKnockProtocol {
	
	private static final int WAITING = 0;
	private static final int SENTKNOCKKNOCK = 1;
	private static final int SENTCLUE = 2;
	private static final int ANOTHER = 3;
	private static final int END = 4;
	
	private static final int NUMJOKES = 1;
	
	public static boolean clientsWaitingServer = false;
	
	private int state = WAITING;
	private int currentJoke = 0;
	
	private String[] clues = {"Noé", "Dem", "Vê"};
	private String[] answers = {"Noé da sua conta!", 
								"Demorou pra responder e eu esqueci a piada!",
								"Vê pelo olho mágico e descobre!"};
	
	public int getState() {
		return state;
	}
	
	public String processInput(String clientInput) {
		
		String output = null;
		
		if(state == WAITING) {
			
			output = "Knock! Knock!";
			state = SENTKNOCKKNOCK;
			
		} else if(state == SENTKNOCKKNOCK) {
			
			if(clientInput.equalsIgnoreCase("quem é?")) {
				
				output = clues[currentJoke];
				state = SENTCLUE;
				
			} else {
				
				output = "Voce deveria dizer 'quem é?'. Tente novamente. \r\nServer: Knock! Knock!";
				
			}
			
		} else if(state == SENTCLUE) {
			
			if(clientInput.equalsIgnoreCase(clues[currentJoke] + " quem?")) {
				
				
				if(clientsWaitingServer && currentJoke == NUMJOKES - 1) {
					output = answers[currentJoke] + ". \r\nNossa fila de espectadores está cheia, por isso, chega de piadas por agora! Se quiser ouvir mais, acesse o servidor novamente e aguarde na fila.";
					state = END;
				} else {
					output = answers[currentJoke] + ". \r\nServer: Quer outra piada? (s/n) ";
					state = ANOTHER;
				}
				
			} else {
				
				output = "Voce deveria dizer '"+ clues[currentJoke] + " quem?'. Tente novamente. \r\nServer: Knock! Knock!";
				state = SENTKNOCKKNOCK;
				
			}
			
		} else if(state == ANOTHER) {
			
			if(clientInput.equalsIgnoreCase("s")) {
				
				output = "Knock! Knock!";
				currentJoke = (currentJoke + 1) % NUMJOKES;
				state = SENTKNOCKKNOCK;
				
			} else {
				
				output = "Tchau.";
				state = END;
				
			}
			
		}
		
		return output;
	}

}
