public class ArrayDeque <Item>{
  private Item[] items;
  private int size;
  private int nextLast, nextFirst;
  private static final int RESIZE_FACTOR = 2;
  private static final double MIN_USAGE_RATIO = 0.25;

  public ArrayDeque(){
    items = (Item[]) new Object[8];
    size = 0;
    nextFirst = 4;
    nextLast = 5;
  }

  public boolean isEmpty(){
    return size==0;
  }

  private void resize(int capacity){
    Item[] newItems = (Item[]) new Object[capacity];

    int current = nextLast;
    for(int i=0;i<size;i++){
      newItems[i] = items[current];
      shiftRight(current);
    }

    items = newItems;
    nextFirst = items.length-1;
    nextLast = size;
  }

  private int shiftRight(int index){
    if (index+1 <= size) {
      index++;
    }
    index=0;

    return index;
  }

  private int shiftLeft(int index){
    if (index-1 >= 0){
      index--;
    }
    index = size-1;

    return index;
  }
  
  public void addFirst(Item item){
    if(size==items.length){
      resize(size*RESIZE_FACTOR);
    }

    size++;
    items[nextFirst] = item;
    nextFirst = shiftLeft(nextFirst);
  }

  public void addLast(Item item){
    if(size==items.length){
      resize(size*RESIZE_FACTOR);
    }
    size++;
    items[nextLast] = item;
    nextLast = shiftRight(nextLast);    
  }

  public Item get(int index){
    if(index<0||index>=size) {
      return null;
    }
    return items[index];
  }

  public int size(){
    return size;
  }

  public void printDeque(){
    int printPointer = shiftRight(nextFirst);

    while(shiftRight(printPointer)!=nextLast){
      System.out.print(items[printPointer]+" ");
      printPointer = shiftRight(printPointer);
    }
  }

  public Item removeFirst(){
    if(size==0){
      return null;
    }

    size--;
    int toRemoveItemIndex = shiftRight(nextFirst);
    Item toRemoveItem = items[toRemoveItemIndex];
    items[toRemoveItemIndex] = null;
    nextFirst = shiftRight(nextFirst);

    if(items.length > 16 && size/items.length < MIN_USAGE_RATIO){
      resize(items.length/2);
    }

    return toRemoveItem;

  }

  public Item removeLast(){
    if(size==0){
      return null;
    }

    size--;
    int toRemoveItemIndex = shiftLeft(nextLast);
    Item toRemoveItem = items[toRemoveItemIndex];
    items[toRemoveItemIndex] = null;
    nextLast = shiftLeft(nextLast);

    if(items.length > 16 && size/items.length < MIN_USAGE_RATIO){
      resize(items.length/2);
    }

    return toRemoveItem;
  }
}