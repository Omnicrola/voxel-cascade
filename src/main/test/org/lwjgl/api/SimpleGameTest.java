package org.lwjgl.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by omnic on 1/10/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class SimpleGameTest {
    private static class TestableSimpleGame extends SimpleGame {

    }

    private static class StopAnswer implements Answer {

        private TestableSimpleGame simpleGame;

        public StopAnswer(TestableSimpleGame simpleGame) {
            this.simpleGame = simpleGame;
        }

        @Override
        public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
            simpleGame.stop();
            return null;
        }
    }

    @Mock
    IGameState mockGameState;

    @Test
    public void testWillRenderAndUpdateSubsystems() throws Exception {
        final TestableSimpleGame simpleGame = new TestableSimpleGame();

        doAnswer(new StopAnswer(simpleGame)).when(mockGameState).render(any(IGameRenderer.class));
        simpleGame.addState(mockGameState);

        verifyNoMoreInteractions(mockGameState);
        simpleGame.start();

        InOrder inOrder = inOrder(mockGameState);
        inOrder.verify(mockGameState).initialize(any(IGameContainer.class));
        inOrder.verify(mockGameState).load(any(IGameContainer.class));
        inOrder.verify(mockGameState).update(any(IGameContainer.class));
        inOrder.verify(mockGameState).render(any(IGameRenderer.class));
        inOrder.verify(mockGameState).unload(any(IGameContainer.class));

        verifyNoMoreInteractions(mockGameState);
    }
}