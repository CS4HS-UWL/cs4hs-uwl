/**
 * Implements two-deck shuffled card simulation.
 * Cards can be iterated through by pressing SPACE.
 *
 * @author M. Allen
 */
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class Flipper implements KeyListener
{
    public static final String[] SUITS = { "clubs", "diamonds", "hearts", "spades" };
    public static final String[] FACES = { "jack", "queen", "king", "ace" };
    
    // Edit the following to make window larger/smaller.
    public static final int WINDOW_SIZE = 800;

    // Edit the following to make # cards in deck larger/smaller.
    private final int deckSize = 40;

    private final double cardHeightRatio = 1.45;
    private final String cardDir = "classic-cards/";
    private final String blueCardBack = cardDir + "back_blue.png";
    private final String redCardBack = cardDir + "back_red.png";
    

    private JFrame window;
    private Image cardLeft, cardRight;
    private boolean playerTurn = true;
    private int cardNumber;
    private String[] leftCards, rightCards;

    /**
     * Simple initiating main().
     * 
     * @param args Not used.
     */
    public static void main( String[] args )
    {
        Flipper flip = new Flipper();
        flip.setUpGame();
    }

    /**
     * Initiates GUI and decks of cards, with shuffling.
     */
    private void setUpGame()
    {
        window = new JFrame( "Pick a Card" );
        window.setBounds( 400, 50, 100, 100 );
        window.setResizable( false );
        window.setVisible( true );
        window.setSize( WINDOW_SIZE + window.getInsets().left + window.getInsets().right,
                        WINDOW_SIZE + window.getInsets().top + window.getInsets().bottom );
        window.setLayout( null );
        window.getContentPane().setBackground( Color.white );
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.addKeyListener( this );

        int imageWidth = ( WINDOW_SIZE - 90 ) / 2;
        int imageHeight = (int) ( cardHeightRatio * imageWidth );
        int leftX = ( WINDOW_SIZE / 4 ) - imageWidth / 2;
        int rightX = 3 * ( WINDOW_SIZE / 4 ) - imageWidth / 2;
        int cardY = ( WINDOW_SIZE - imageHeight ) / 2;
        cardLeft = new Image( leftX, cardY, imageWidth, imageHeight, blueCardBack );
        cardRight = new Image( rightX, cardY, imageWidth, imageHeight, redCardBack );
        window.add( cardLeft );
        window.add( cardRight );
        window.repaint();

        leftCards = fillCards( deckSize );
        shuffleCards( leftCards );
        rightCards = fillCards( deckSize );
        shuffleCards( rightCards );

        // Remove comments on following lines to generate a simulation that
        // plays 200 rounds of game, contesting a purely random strategy to the
        // one learned by the RL approach.

        // String[] bigCards1 = fillCards( 200 );
        // shuffleCards( bigCards1 );
        // String[] bigCards2 = fillCards( 200 );
        // shuffleCards( bigCards2 );
        // PlaySimulator sim = new PlaySimulator( bigCards1, bigCards2 );
        // sim.simulate();
    }

    /**
     * Generates a deck of cards containing multiple instances of each of the
     * possible face cards (Jack, Queen, King, Ace).
     * 
     * @param deckSize Number of cards to place in the deck.
     * 
     * @return Array of file-names, each naming one of the card images supplied.
     */
    private String[] fillCards( int deckSize )
    {
        String[] cards = new String[deckSize];
        int count = 0;
        int s = 0;
        while ( count < cards.length )
        {
            String suit = SUITS[s % SUITS.length];
            s++ ;
            int f = 0;
            while ( count < cards.length && f < FACES.length )
            {
                String face = FACES[f];
                cards[count] = String.format( "%s%s_of_%s.png", cardDir, face, suit );
                f++ ;
                count++ ;
            }
        }
        return cards;
    }

    /**
     * Randomly shuffles a deck of cards; uses the Knuth/Fisher/Yates shuffle.
     * 
     * @param deck Array of file-names, each naming one of the card images
     *            supplied.
     */
    private void shuffleCards( String[] deck )
    {
        for ( int i = deck.length - 1; i > 0; i-- )
        {
            int j = (int) ( Math.random() * i );
            String temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
    }

    /**
     * Responds to presses of Space-bar, iterating through each of the decks in
     * turn. Will stop at end of the decks when no more cards remain.
     * 
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    @Override
    public void keyPressed( KeyEvent e )
    {
        if ( e.getKeyCode() == KeyEvent.VK_SPACE &&
             cardNumber < rightCards.length )
        {
            if ( playerTurn )
            {
                cardLeft.setImage( leftCards[cardNumber] );
                cardRight.setImage( redCardBack );
            }
            else
            {
                cardRight.setImage( rightCards[cardNumber] );
                cardNumber++ ;
            }
            playerTurn = !playerTurn;
            window.repaint();
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    @Override
    public void keyTyped( KeyEvent e )
    {
        // Not used; present for interface compliance.
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    @Override
    public void keyReleased( KeyEvent e )
    {
        // Not used; present for interface compliance.
    }
}
