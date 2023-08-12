package protocol;

public class KnockKnockProtocol {
	
	private static final int WAITING = 0;
	private static final int SENTKNOCKKNOCK = 1;
	private static final int SENTCLUE = 2;
	private static final int ANOTHER = 3;
	
	private static final int NUMJOKES = 3;
	
	private int state = WAITING;
	private int currentJoke = 0;
	
	private String[] clues = {"Noe", "Dem", "Ve"};
	private String[] answers = {"Noe da sua conta!", 
								"Demorou pra responder e eu esqueci a piada!",
								"Ve pelo olho magico e descobre!"};
	
	public String processInput(String clientInput) {
		
		String output = null;
		
		if(state == WAITING) {
			
			output = "Knock! Knock! á";
			state = SENTKNOCKKNOCK;
			
		} else if(state == SENTKNOCKKNOCK) {
			
			if(clientInput.equalsIgnoreCase("quem e?")) {
				
				output = clues[currentJoke];
				state = SENTCLUE;
				
			} else {
				
				output = "Voce deveria dizer 'quem e?'. Tente novamente. \r\nServer: Knock! Knock!";
				
			}
			
		} else if(state == SENTCLUE) {
			
			if(clientInput.equalsIgnoreCase(clues[currentJoke] + " quem?")) {
				
				output = answers[currentJoke] + ". \r\nServer: Quer outra piada? (s/n) ";
				state = ANOTHER;
				
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
				state = WAITING;
				
			}
			
		}
		
		return output;
	}

}
