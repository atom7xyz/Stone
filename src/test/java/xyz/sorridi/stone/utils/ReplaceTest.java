package xyz.sorridi.stone.utils;

import org.junit.jupiter.api.Test;
import xyz.sorridi.stone.utils.data.Array;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReplaceTest
{

    @Test
    void ofString()
    {
        var what = Array.of("Hello %p!", "How's your day? %a");
        var toReplace = Array.of("%p", "%a");
        var replaceWith = Array.of("Sorridi", ":)");

        for (int i = 0; i < 5; i++)
        {
            assertEquals(Replace.of("Ciao %p!", "%p", "Sorridi"), "Ciao Sorridi!");
            assertEquals(Replace.of("Hello %p!", "%p", 100), "Hello 100!");
            assertEquals(Replace.of("Sveiki %p!", "%p", false), "Sveiki false!");

            assertEquals(Replace.of("Ciao %p! %a", toReplace, replaceWith), "Ciao Sorridi! :)");
            assertEquals(Replace.of("Hello %p! %a", toReplace, replaceWith), "Hello Sorridi! :)");
            assertEquals(Replace.of("Sveiki %p! %a", toReplace, replaceWith), "Sveiki Sorridi! :)");

            assertEquals(Replace.of(what, toReplace[0], replaceWith[0])[0], "Hello Sorridi!");
            assertEquals(Replace.of(what, toReplace[1], replaceWith[1])[1], "How's your day? :)");
        }
    }

    @Test
    void ofStringArray()
    {
        var what = Array.of("Hello %p!", "How's your day? %a");
        var toReplace = Array.of("%p", "%a");
        var replaceWith = Array.of("Sorridi", ":)");

        for (int i = 0; i < 5; i++)
        {
            assertEquals(Replace.of(what, toReplace, replaceWith)[0], "Hello Sorridi!");
            assertEquals(Replace.of(what, toReplace, replaceWith)[1], "How's your day? :)");
        }
    }

    @Test
    void ofCollection()
    {
        List<String> list = List.of("Hello %p!", "Ciao %p!", "Sveiki %p!");
        List<String> res = List.of("Hello Sorridi!", "Ciao Sorridi!", "Sveiki Sorridi!");

        for (int i = 0; i < 5; i++)
        {
            assertEquals(Replace.of(list, "%p", "Sorridi"), res);
        }

        list = List.of("Hello %p!", "How's your day? %a");
        res = List.of("Hello Sorridi!", "How's your day? :)");

        var toReplace = Array.of("%p", "%a");
        var replaceWith = Array.of("Sorridi", ":)");

        for (int i = 0; i < 5; i++)
        {
            assertEquals(Replace.of(list, toReplace, replaceWith), res);
        }
    }

}