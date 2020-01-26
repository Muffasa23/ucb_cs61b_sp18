public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextLast, nextFirst;
    private static final int RESIZE_FACTOR = 2;
    private static final double MIN_USAGE_RATIO = 0.25;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];

        int current = nextLast;
        for (int i = 0; i < size; i++) {
            newItems[i] = items[current];
            current = shiftRight(current);
        }

        items = newItems;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    private int shiftRight(int index) {
        if (index + 1 < items.length) {
            index++;
            return index;
        }
        index = 0;

        return index;
    }

    private int shiftLeft(int index) {
        if (index - 1 > 0) {
            index--;
            return index;
        }
        index = size - 1;

        return index;
    }
    
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * RESIZE_FACTOR);
        }

        size++;
        items[nextFirst] = item;
        nextFirst = shiftLeft(nextFirst);
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size * RESIZE_FACTOR);
        }
        size++;
        items[nextLast] = item;
        nextLast = shiftRight(nextLast);    
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int pointer = shiftRight(nextFirst);
        while (index > 0) {
            pointer = shiftRight(pointer);
            index--;
        }

        return items[pointer];
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int printPointer = shiftRight(nextFirst);

        while (shiftRight(printPointer) != nextLast) {
            System.out.print(items[printPointer] + " ");
            printPointer = shiftRight(printPointer);
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        size--;
        int toRemoveItemIndex = shiftRight(nextFirst);
        T toRemoveItem = items[toRemoveItemIndex];
        items[toRemoveItemIndex] = null;
        nextFirst = shiftRight(nextFirst);

        if (items.length > 16 && size / items.length < MIN_USAGE_RATIO) {
            resize(items.length / 2);
        }

        return toRemoveItem;

    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }

        size--;
        int toRemoveItemIndex = shiftLeft(nextLast);
        T toRemoveItem = items[toRemoveItemIndex];
        items[toRemoveItemIndex] = null;
        nextLast = shiftLeft(nextLast);

        if (items.length > 16 && size / items.length < MIN_USAGE_RATIO) {
            resize(items.length / 2);
        }

        return toRemoveItem;
    }
}
