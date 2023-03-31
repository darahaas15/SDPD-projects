package androidsamples.java.dicegames;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class WalletTest {


  @Test
  public void addition_isCorrect() {
    assertEquals(4, 2 + 2);
  }

  @Test
  public void addition_isCorrect2(){
    Die6 die = mock(Die6.class);
    when(die.value()).thenReturn(6).thenReturn(6).thenReturn(6).thenReturn(3).thenReturn(3).thenReturn(6).thenReturn(1).thenReturn(6).thenReturn(6).thenReturn(5).thenReturn(4);

    WalletViewModel w = spy(WalletViewModel.class);
    w.mDie = die;
    for(int i= 0; i< 8; i++){
      w.rollDie();
      System.out.println(w.balance());
    }

    assertEquals(40, w.balance());
  }

  @Test
  public void addition_isCorrect3(){
    Die6 die = mock(Die6.class);
    when(die.value()).thenReturn(1).thenReturn(2).thenReturn(6).thenReturn(3).thenReturn(3);

    WalletViewModel w = spy(WalletViewModel.class);
    w.mDie = die;
    for(int i= 0; i< 8; i++){
      w.rollDie();
      System.out.println(w.balance());
    }

    assertEquals(0, w.balance());
  }

    @Test
    public void addition_isCorrect4(){
        Die6 die = mock(Die6.class);
        when(die.value()).thenReturn(6).thenReturn(6).thenReturn(6);

        WalletViewModel w = spy(WalletViewModel.class);
        w.mDie = die;
        for(int i= 0; i< 8; i++){
            w.rollDie();
            System.out.println(w.balance());
        }

        assertEquals(25, w.balance());
    }


}