package com.example.bellosil21.pokertexasholdem;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.bellosil21.pokertexasholdem.Poker.Money.PlayerChipCollection;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private Object ArrayList;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.bellosil21.pokertexasholdem", appContext.getPackageName());
    }

    @Test
    public void TestTurnTracker(){
        ArrayList<PlayerChipCollection> player;
        int activePlayerID;
        //TurnTracker test = new TurnTracker();

        // tests for using TurnTracker with incorrect parameters
        //TestTurnTracker(ArrayList<PlayerChipCollection> player);
        //TestTurnTracker(int activePlayerID);
    }
}
