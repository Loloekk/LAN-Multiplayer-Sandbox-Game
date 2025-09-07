package io.github.terraria.logic;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class MultisetItemHolderTest {
    @Test
    void newItemHolderIsEmpty() {
        ItemHolder holder = new MultisetItemHolder(10);
        assertThat(holder.browse()).isEmpty();
    }
    @Test
    void constructorArgumentCoalescesToNonnegative() {
        ItemHolder holder = new MultisetItemHolder(-3);
        assertThat(holder.browse()).isEmpty();
        assertFalse(holder.insert(Mockito.mock(Item.class)));
    }
}
