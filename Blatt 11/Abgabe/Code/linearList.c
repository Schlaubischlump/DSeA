for ( ; n>0; n--) {
        insert(F, getMin(F)+1);
        insert(F, getMin(F)-1);
        insert(F, getMin(F)-2);
        deleteMin(F);
        x = min(F).childs[0]; // immer das größte Kind des Minimums
        decreaseKey(x, getMin(F)-2);
        deleteMin(F);
}