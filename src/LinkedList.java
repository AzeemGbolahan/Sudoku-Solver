/*
file name:      LinkedList.java
Authors:        Azeem Gbolahan
last modified:  03/12/2025
implements a linkedlist structure to be used for the stack
How to run:     java -ea JobMaker
*/

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic singly linked list implementation with basic operations.
 * Supports iteration, element access, addition, removal, and checking list properties.
 */
public class LinkedList<T> implements Queue<T>,  Stack<T>, Iterable<T> {

    // Iterator class for traversing the linked list
    private class LLIterator implements Iterator<T> {
        private Node current; // Tracks the next node in traversal

        public LLIterator(Node head) {
            this.current = head;
        }

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the list.");
            }
            T data = current.getData();
            current = current.next;
            return data;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove operation is not supported.");
        }
    }

    // Returns an iterator for the linked list
    public Iterator<T> iterator() {
        return new LLIterator(this.head);
    }

    // Inner class representing a node in the linked list
    private class Node {
        private Node next;
        private T item;

        public Node(T item) {
            this.item = item;
            this.next = null;
        }

        public T getData() {
            return this.item;
        }

        public void setNext(Node n) {
            this.next = n;
        }
    }

    private Node head; // First node of the linked list
    private Node tail; // Last node of the linked list
    private int size;  // Number of elements in the linked list

    // Constructor initializes an empty linked list
    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Returns the number of elements in the linked list
    public int size() {
        return this.size;
    }

    // Clears the linked list
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Checks if the linked list is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Returns a string representation of the linked list
    public String toString() {
        if (this.head == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        Node current = this.head;
        while (current != null) {
            sb.append(current.item);
            if (current.next != null) {
                sb.append(" --> ");
            }
            current = current.next;
        }
        return sb.toString();
    }

    // Checks if a specific element exists in the linked list
    public boolean contains(Object o) {
        Node current = this.head;
        while (current != null) {
            if (current.item.equals(o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Adds an element at the end of the linked list
    public void push(T item) {
        this.add(item);
    }
    // Adds an element at the end of the linked list
    public void offer(T item) {
        this.add(item);
    }

    // Returns the last element without removing it
    public T peek() {
        return this.head.item;
    }

    // Removes and returns the first element of the list / at the top of the stack
    public T pop() {
        return this.remove();
    }

    // Removes and returns the first element of the list / at the top of the stack
    public T poll() {
        return this.remove();
    }

    // Retrieves the element at the specified index
    public T get(int index) {
        if (index < 0 || index >= this.size) {
            return null;
        }

        Node current = this.head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }   
        return current.getData();
    }
    // Adds an element at the beginning of the list
    public void add(T item) {
        Node newNode = new Node(item);
        newNode.setNext(this.head);
        this.head = newNode;
        if (this.size == 0) {
            this.tail = newNode; // Update tail if list was empty
        }
        this.size++;
    }

    // Removes and returns the first element of the list
    public T remove() {
        if (this.head == null) {
            return null;
        }

        T removed = this.head.item;
        this.head = this.head.next;
        this.size--;
        if (this.size == 0) {
            this.tail = null;
        }
        return removed;
    }

    // Compares two linked lists for equality
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Same reference
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // Null or different class
        }

        LinkedList<?> other = (LinkedList<?>) obj;

        if (this.size != other.size) {
            return false; // Different sizes
        }

        LinkedList<T>.Node currentA = this.head;  // Correctly reference the inner Node class
        LinkedList<?>.Node currentB = other.head; // Explicitly reference 'other' Node

        while (currentA != null && currentB != null) {
            if (!currentA.getData().equals(currentB.getData())) {
                return false; // Elements are not equal
            }
            currentA = currentA.next;
            currentB = currentB.next;
        }

        return true; // All elements matched
    }

    // Adds an element at a specified index
    public void add(int index, T item) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
        }

        Node newNode = new Node(item);

        if (index == 0) {
            newNode.setNext(this.head);
            this.head = newNode;
            if (this.size == 0) {
                this.tail = newNode;
            }
        } else {
            Node current = this.head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            newNode.setNext(current.next);
            current.setNext(newNode);
            if (index == this.size) {
                this.tail = newNode; // Update tail if inserting at the end
            }
        }
        this.size++;
    }

    // Removes and returns the element at a specified index
    public T remove(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
        }
        if (index == 0) {
            return this.remove();
        }
        if (index == this.size - 1) {
            return this.removeLast();
        }

        Node current = this.head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        T removed = current.next.getData();
        current.next = current.next.next;
        this.size--;
        return removed;
    }

    // Adds an element at the end of the list
    public void addLast(T item) {
        Node itemNode = new Node(item);
        if (this.head == null) {
            this.head = itemNode;
            this.tail = itemNode;
        } else {
            this.tail.next = itemNode;
            this.tail = itemNode;
        }
        size++;
    }

    // Removes and returns the last element of the list
    public T removeLast() {
        if (this.head == null) {
            throw new NoSuchElementException("The list is empty");
        }
        if (this.head == this.tail) {
            T removed = this.head.getData();
            this.head = null;
            this.tail = null;
            size = 0;
            return removed;
        }

        Node current = this.head;
        while (current.next != this.tail) {
            current = current.next;
        }

        T removed = this.tail.getData();
        this.tail = current;
        this.tail.next = null;
        size--;
        return removed;
    }

    // Returns the last element without removing it
    public T getLast() {
        if (this.tail == null) {
            throw new NoSuchElementException("The list is empty");
        }
        return this.tail.getData();
    }
}
