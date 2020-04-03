public class LinkedListDeque<T> implements Deque<T> {
    private class Node {
        private Node prev;
        private T item;
        private Node next;

        public Node(final Node prev, final T item, final Node next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private final Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(final T item) {
        Node newFirst = new Node(sentinel, item, sentinel.next);
        sentinel.next.prev = newFirst;
        sentinel.next = newFirst;
        size++;
    }

    @Override
    public void addLast(final T item) {
        Node newLast = new Node(sentinel.prev, item, sentinel);
        sentinel.prev.next = newLast;
        sentinel.prev = newLast;
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node ptr = sentinel;
        ptr = ptr.next;
        while (ptr.next != sentinel) {
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
    }

    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        } else {
            Node newFirst = sentinel.next.next;
            Node removedNode = sentinel.next;
            newFirst.prev = sentinel;
            sentinel.next = newFirst;
            size--;
            return removedNode.item;
        }
    }

    @Override
    public T removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        } else {
            Node newLast = sentinel.prev.prev;
            Node removedNode = sentinel.prev;
            newLast.next = sentinel;
            sentinel.prev = newLast;
            size--;
            return removedNode.item;
        }
    }

    public T get(final int index) {
        if (size <= index || index < 0) {
            return null;
        }
        Node ptr = sentinel;
        ptr = ptr.next;
        int count = 0;

        while (count < index) {
            ptr = ptr.next;
            count++;
        }

        return ptr.item;
    }

    private T getRecursive(Node ptr, int index) {
        if (index == 0) {
            return ptr.item;
        }
        return getRecursive(ptr.next, index - 1);
    }

    public T getRecursive(final int index) {
        if (size <= index || index < 0) {
            return null;
        }
        return getRecursive(sentinel.next, index);
    }

}
