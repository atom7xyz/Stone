package xyz.sorridi.stone.utils;

import lombok.val;
import org.junit.jupiter.api.Test;
import xyz.sorridi.stone.utils.data.Array;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReplaceTest
{

    @Test
    void ofString()
    {
        assertEquals(Replace.of("Ciao %p!", "%p", "Sorridi"), "Ciao Sorridi!");
        assertEquals(Replace.of("Hello %p!", "%p", 100), "Hello 100!");
        assertEquals(Replace.of("Sveiki %p!", "%p", false), "Sveiki false!");

        val what        = Array.of("Hello %p!", "How's your day? %a");
        val toReplace   = Array.of("%p", "%a");
        val replaceWith = Array.of("Sorridi", ":)");

        assertEquals(Replace.of("Ciao %p! %a", toReplace, replaceWith), "Ciao Sorridi! :)");
        assertEquals(Replace.of("Hello %p! %a", toReplace, replaceWith), "Hello Sorridi! :)");
        assertEquals(Replace.of("Sveiki %p! %a", toReplace, replaceWith), "Sveiki Sorridi! :)");

        assertEquals(Replace.of(what, toReplace[0], replaceWith[0])[0], "Hello Sorridi!");
        assertEquals(Replace.of(what, toReplace[1], replaceWith[1])[1], "How's your day? :)");
    }

    @Test
    void ofStringArray()
    {
        val what        = Array.of("Hello %p!", "How's your day? %a");
        val toReplace   = Array.of("%p", "%a");
        val replaceWith = Array.of("Sorridi", ":)");

        assertEquals(Replace.of(what, toReplace, replaceWith)[0], "Hello Sorridi!");
        assertEquals(Replace.of(what, toReplace, replaceWith)[1], "How's your day? :)");
    }

    @Test
    void ofCollection()
    {
        List<String> list = List.of("Hello %p!", "Ciao %p!", "Sveiki %p!");

        assertEquals(Replace.of(list, "%p", "Sorridi"), Replace.of(list, "%p", "Sorridi"));

        list            = List.of("Hello %p!", "How's your day? %a");
        val what        = Array.of("Hello %p!", "How's your day? %a");
        val toReplace   = Array.of("%p", "%a");
        val replaceWith = Array.of("Sorridi", ":)");

        assertEquals(Replace.of(list, toReplace, replaceWith), Arrays.stream(Replace.of(what, toReplace, replaceWith)).toList());
    }

}