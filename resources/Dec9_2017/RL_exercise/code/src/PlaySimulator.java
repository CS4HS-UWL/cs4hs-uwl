/**
 * Simulates game-play for two players, one who always chooses actions randomly,
 * the other who chooses them based upon an elementary reinforcement learning
 * scheme. Prints out the results in pairs:
 * <RANDOM_PLAYER_SCORE>,<LEARNER_SCORE>
 * so that these can be dumped to a CSV and plotted if desired.
 * 
 * @author M. Allen
 */

public class PlaySimulator
{
    String[] playerDeck, opponentDeck;
    private int[][] qVals = new int[Flipper.FACES.length][2];
    private final int higher = 0;
    private final int lower = 1;

    /**
     * Generates a simulator for the two filled and shuffled decks given.
     * 
     * @param deck1 Deck from which players draw cards.
     * @param deck2 Deck against which cards are compared.
     */
    public PlaySimulator( String[] deck1, String[] deck2 )
    {
        playerDeck = deck1;
        opponentDeck = deck2;
    }

    /**
     * Iterates through the pair of decks, generating results for a purely
     * random versus a learning strategy.
     */
    public void simulate()
    {
        System.out.println( "Simulating..." );

        for ( int i = 0; i < playerDeck.length; i++ )
        {
            String card1 = playerDeck[i];
            String card2 = opponentDeck[i];
            String face1 = getFace( card1 );
            String face2 = getFace( card2 );

            int randomAction = (int) ( Math.random() * 2 );
            int learnedAction = chooseLearnedAction( face1 );
            int result = compare( face1, face2 );
            if ( result == 0 )
            {
                // Tie.
                System.out.println( "0,0" );
            }
            else if ( result < 0 )
            {
                // Player card less than opponent.
                System.out.print( ( randomAction == higher ) ? "1," : "-1," );
                int score = ( learnedAction == higher ) ? 1 : -1;
                System.out.println( score );
                updateQ( face1, learnedAction, score );
            }
            else
            {
                // Player card greater than opponent.
                System.out.print( ( randomAction == higher ) ? "-1," : "1," );
                int score = ( learnedAction == higher ) ? -1 : 1;
                System.out.println( score );
                updateQ( face1, learnedAction, score );
            }
        }
    }

    /**
     * Returns the face-value of a given card. Relies on naming conventions in
     * the Flipper class and supplied card-image files.
     * 
     * @param card String giving full path and file-name of a card image-file.
     * 
     * @return The face-value of the card.
     */
    private String getFace( String card )
    {
        String face = card.substring( 0, card.indexOf( '_' ) );
        int start = face.indexOf( '/' ) + 1;
        return face.substring( start );
    }

    /**
     * Generates a reinforcement-learning based action choice.
     * 
     * @param face A face-value for a card in the player's deck.
     * 
     * @return An action choice (higher/lower) with highest recorded Q-value;
     *         ties broken randomly.
     */
    private int chooseLearnedAction( String face )
    {
        int qIndex = 0;
        for ( int i = 1; i < Flipper.FACES.length; i++ )
        {
            if ( Flipper.FACES[i].equals( face ) )
            {
                qIndex = i;
            }
        }

        int action = higher;
        if ( qVals[qIndex][lower] > qVals[qIndex][higher] )
        {
            action = lower;
        }
        else if ( qVals[qIndex][lower] == qVals[qIndex][higher] )
        {
            action = (int) ( Math.random() * 2 );
        }
        return action;
    }

    /**
     * Compares two face-card values.
     * 
     * @param face1 Value of player card.
     * @param face2 Value of opponent card.
     * 
     * @return Numerical comparison, as compareTo(), where:
     *         0 == (face1 == face2)
     *         (n < 0) == (face1 < face2)
     *         (n > 0) == (face2 > face1)
     */
    private int compare( String face1, String face2 )
    {
        if ( face1.equals( face2 ) )
        {
            return 0;
        }

        int index1 = 0;
        int index2 = 0;
        for ( int i = 1; i < Flipper.FACES.length; i++ )
        {
            if ( Flipper.FACES[i].equals( face1 ) )
            {
                index1 = i;
            }
            else if ( Flipper.FACES[i].equals( face2 ) )
            {
                index2 = i;
            }
        }
        return index1 - index2;
    }

    /**
     * Updates Q-values of (state,action) pairs based upon results.
     * 
     * @param face Card face-value (state of system).
     * @param action Action taken by player.
     * @param score Result of taking action (-1/0/+1).
     */
    private void updateQ( String face, int action, int score )
    {
        int qIndex = 0;
        for ( int i = 1; i < Flipper.FACES.length; i++ )
        {
            if ( Flipper.FACES[i].equals( face ) )
            {
                qIndex = i;
            }
        }
        qVals[qIndex][action] += score;
    }
}
