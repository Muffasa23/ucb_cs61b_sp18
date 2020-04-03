public class ArrayDeque<T> implements Deque<T> {
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

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];

        int current = shiftRight(nextFirst);
        for (int i = 0; i < size; i++) {
            newItems[i] = items[current];
            current = shiftRight(current);
        }

        items = newItems;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    private int shiftRight(int index) {
        return (index + 1) % items.length;
    }

    private int shiftLeft(int index) {
        return (items.length + index - 1) % items.length;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * RESIZE_FACTOR);
        }

        items[nextFirst] = item;
        size++;
        nextFirst = shiftLeft(nextFirst);
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * RESIZE_FACTOR);
        }

        items[nextLast] = item;
        size++;
        nextLast = shiftRight(nextLast);
    }

    public T get(int index) {
        if (index < 0 || index > size) {
            return null;
        }

        int position = (shiftRight(nextFirst) + index) % items.length;
        return items[position];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int printPointer = shiftRight(nextFirst);

        while (printPointer != nextLast) {
            System.out.print(items[printPointer] + " ");
            printPointer = shiftRight(printPointer);
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        int toRemoveItemIndex = shiftRight(nextFirst);
        T toRemoveItem = items[toRemoveItemIndex];
        items[toRemoveItemIndex] = null;
        nextFirst = toRemoveItemIndex;
        size--;

        if (items.length >= 16 && size < items.length * MIN_USAGE_RATIO) {
            resize(items.length / 2);
        }

        return toRemoveItem;

    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        int toRemoveItemIndex = shiftLeft(nextLast);
        T toRemoveItem = items[toRemoveItemIndex];
        items[toRemoveItemIndex] = null;
        nextLast = toRemoveItemIndex;
        size--;

        if (items.length >= 16 && size < items.length * MIN_USAGE_RATIO) {
            resize(items.length / 2);
        }

        return toRemoveItem;
    }
}
