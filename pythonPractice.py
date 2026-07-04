""" 
                 --LINKED LISTS--
class Node:
    def __init__(self, data):
        self.data = data # Stores a number(data)
        self.next = None # Points to the next node(if there is nothing, outputs null)

def traverseAndPrint(head):
    currentNode = head # starts at the first box
    while currentNode: # Keep going while there is a box
        print(currentNode.data, end="-->") # prints out the data
        currentNode = currentNode.next # moves to the next box
    print("null") # print "null" when we reach the end
# below  is the chain of data
node1 = Node(7)
node2 = Node(11)
node3 = Node(3)
node4 = Node(2)
node5 = Node(9)
# This is where the data is linked together
node1.next = node2
node2.next = node3
node3.next = node4
node4.next = node5

traverseAndPrint(node1)
"""
class Node:
    def __init__(self, data):
        self.data = data
        self.next = None

def findLowestValue(head): # function to find the lowest value in the linked list
    minValue = head.data # Sets the minimum value to the data in node 1 and stores it in minValue
    currentNode = head.next
    while currentNode:
        if currentNode.data < minValue: # when the next value is less than the head value, it updates the minValue variable
            minValue = currentNode.data
        currentNode = currentNode.next
    return minValue

node1 = Node(7)
node2 = Node(11)
node3 = Node(3)
node4 = Node(2)
node5 = Node(9)

node1.next = node2
node2.next = node3
node3.next = node4
node4.next = node5

print("The lowest value in the linked list is :",
      findLowestValue(node1))


class Node:
    def __init__(self, data):
        self.data = data
        self.next = None

def traverseAndPrint(head):
    currentNode = head
    while currentNode:
        print(currentNode.data, end="-->")
        currentNode = currentNode.next
    print("Null")

def deleteSpecificNode(head, nodeToDelete):
    if head == nodeToDelete:
        return head.next # if the head is the box we want to delete, then it just makes the next node the head

    currentNode = head
    while currentNode.next and currentNode.next != nodeToDelete:
        currentNode = currentNode.next # keep moving foward until we find the right node to delete


    if currentNode.next is None:
        return head

    currentNode.next = currentNode.next.next   

    return head

node1 = Node(8)
node2 = Node(7)
node3 = Node(11)
node4 = Node(17)
node5 = Node(3)

node1.next = node2
node2.next = node3
node3.next = node4
node4.next = node5

node1 = deleteSpecificNode(node1, node4)
print("\nAfter deletion:")
traverseAndPrint(node1)
  
            # --- HASH TABLES---
# creating a Hash Table

My_list = [None, None, None, None, None, None, None, None, None, None]

def hash_func(value):
    sum_of_chars = 0
    for char in value:
        sum_of_chars += ord(char)

    return sum_of_chars % 10

print("'Bob' has hash code:", hash_func('Bob'))  

def add(name):
    index = hash_func(name)
    My_list[index] = name

def contains(name):
    index = hash_func(name)
    return My_list[index] == name


add('Bob')
add('Lisa')
add('Pete')
add('Jones')
add('Siri')
print(My_list)

print("'Pete' is in the Hash table:", contains('Pete'))

# TREES, now we are entering algorithms

#  (1) BINARY TREES
class TreeNode:
    def __init__(self, data):
        self.data= data
        self.left= None
        self.right= None

root =TreeNode('R')
nodeA = TreeNode('A')
nodeB =  TreeNode('B')
nodeC = TreeNode('C')
nodeD = TreeNode('D')
nodeE = TreeNode('E')
nodeF = TreeNode('F')
nodeG = TreeNode('G')

root.left = nodeA
root.right = nodeB

nodeA.left = nodeC
nodeA.right = nodeD

nodeB.left = nodeE
nodeB.right = nodeF

nodeF.left = nodeG

print("root.right.left.data:", root.right.left.data)

"""
 Binary trasversal techniques
  1. Breadth First Search
  2. Depth First Search
   There are 3 types of DFS trasversal methods;
    a) Pre-order
    b) In-order
    c) post-order
""" 
 # PRE-ORDER TRAVERSAL OF BINARY TREES --> visits the root node first, then do a search on the left subtree, followed by the right tree

def preOrderTraversal(node):
    if node is None: # base case: if there is no node, go back
        return
    print(node.data, end=",") # visit the current node first
    preOrderTraversal(node.left) # visit all the nodes on the left
    preOrderTraversal(node.right) # visit all the nodes on the right

preOrderTraversal(root)
print("\n") 

# IN-ORDER TRAVERSAL OF BINARY TREES --> First does a search on the left subtree, visits the node, and finally does a search on the right subtree

def inOrderTraversal(node):
    if node is None:
        return #base case of the recursive call
    inOrderTraversal(node.left) # visits the left subtree first
    print(node.data, end=",") # then visits the root node
    inOrderTraversal(node.right)# finally visits the right subtree

inOrderTraversal(root)
