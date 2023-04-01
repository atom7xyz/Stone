package xyz.sorridi.stone.utils;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
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

        val what        = new String[] { "Hello %p!", "How's your day? %a" };
        val toReplace   = new String[] { "%p", "%a" };
        val replaceWith = new String[] { "Sorridi", ":)" };

        assertEquals(Replace.of("Ciao %p! %a", toReplace, replaceWith), "Ciao Sorridi! :)");
    }

    @Test
    void ofStringArray()
    {
        val what        = new String[] { "Hello %p!", "How's your day? %a" };
        val toReplace   = new String[] { "%p", "%a" };
        val replaceWith = new String[] { "Sorridi", ":)" };

        assertEquals(Replace.of(what, toReplace, replaceWith)[0], "Hello Sorridi!");
        assertEquals(Replace.of(what, toReplace, replaceWith)[1], "How's your day? :)");
    }

    @Test
    void ofCollection()
    {
        List<String> list = List.of("Hello %p!", "Ciao %p!", "Sveiki %p!");
        val collection  = Collections.unmodifiableCollection(list);
        val array       = list.toArray(new String[0]);

        assertEquals(Replace.of(list, "%p", "Sorridi"), Replace.of(collection, "%p", "Sorridi"));

        list            = List.of("Hello %p!", "How's your day? %a");
        val what        = new String[] { "Hello %p!", "How's your day? %a" };
        val toReplace   = new String[] { "%p", "%a" };
        val replaceWith = new String[] { "Sorridi", ":)" };

        assertEquals(Replace.of(list, toReplace, replaceWith), Arrays.stream(Replace.of(what, toReplace, replaceWith)).toList());
    }

}