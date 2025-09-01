package io.github.terraria.logic;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class ItemHolderTest {
    private static final int cap = 10;
    private static class DummyItemHolder extends ItemHolder {
        public DummyItemHolder() {
            super(cap);
        }
        public DummyItemHolder(int cap) {
            super(cap);
        }
    }
    @Test
    void newItemHolderIsEmpty() {
        ItemHolder holder = new DummyItemHolder();
        assertThat(holder.browse()).isEmpty();
    }
    @Test
    void constructorArgumentCoalescesToNonnegative() {
        ItemHolder holder = new DummyItemHolder(-3);
        assertThat(holder.browse()).isEmpty();
        assertFalse(holder.insert(Mockito.mock(Item.class)));
    }
}
