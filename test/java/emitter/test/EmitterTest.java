package emitter.test;

import com.github.wenj91.engine.io.emitter.Emitter;
import com.github.wenj91.engine.io.emitter.listener.Listener;
import org.junit.Test;

/**
 * Created by wenj91 on 2016-11-21.
 */
public class EmitterTest {

    @Test
    public void onTest(){
        Emitter emitter = new Emitter();
        emitter.on("event", new Listener() {
            @Override
            public void call(Object... args) {
                System.out.print("event1:");
                for(Object obj:args){
                    System.out.print(obj);
                }
                System.out.println();
            }
        });
        emitter.on("event", new Listener() {
            @Override
            public void call(Object... args) {
                System.out.print("event2:");
                for(Object obj:args){
                    System.out.print(obj);
                }
                System.out.println();
            }
        });

        emitter.emit("event", 111);
        emitter.emit("event", 1111);
    }

    @Test
    public void offTest(){

    }

    @Test
    public void emitTest(){

    }
}
