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
print("\n")

# POST-ORDER TRAVERSAL OF BINARY TREES --> It works by recursively doing a post order of the left subtree and the right subtree, followed by a visit to the root node.
def postOrderTrasversal(node):
  if node is None: # base case
    return
  postOrderTrasversal(node.left) # visits the left node
  postOrderTrasversal(node.right) # visits the right node
  print(node.data, end=",") # finallly visits the node

postOrderTrasversal(root) 
print("\n") 

""" 
 BINARY SEARCH TRESS --> It is a type of binary tree data structure, where the following properties must be true for any node "X" in the tree
  1) The X node's left child and all of its decendants have lower
     values than X
  2) The right child, and all its decendants have higher values than X
  3) Left and right subtrees must also be Binary Search Trees.    
"""

# TRAVERSAL OF A BINARY SEARCH TREE

class TreeNode:
    def __init__(self, info):
        self.info = info
        self.left = None
        self.right = None 

def inOrderTrasversal(node):
    if node is None:
        return
    inOrderTrasversal(node.left)
    print(node.info, end=",")
    inOrderTrasversal(node.right)

# searching for a node in BST
def search(node,target):
    if target is None:
        return None
    elif node.info == target:
        return node
    elif target < node.info:
        return search(node.left, target)
    else:
        return search(node.right, target)
# Inserting a new node in a BST
def insertNode(node, info):
    if node is None:
        return TreeNode(info)
    else:
        if info < node.info:
            node.left = insertNode(node.left, info)
        elif info > node.info:
            node.right = insertNode(node.right, info) 
    return node 
# finding lowest value in a BST(left subtree)
def lowestNode(node):
    current = node
    while current.left is not None:
        current = current.left
    return current               
# deleting a node in BST
def deleteNode(node, info):
    if not node:
        return None # makes the function to be able to call itself recursively
    
    if info < node.info:
        node.left = deleteNode(node.left, info) # if the value to be deleted is smaller than the current node's value, go to the left subtree
    elif info > node.info:
        node.right = deleteNode(node.right, info) # if the value to be deleted is larger than the current node value, go right
    else: # we found the node to delete
        #Node with only one child or no child
         if not node.left: # if no left child
             temp = node.right # save the right child
             node = None # delete the current node and set to none
             return temp # return the right child to replace this node
         elif not node.right: # if no right child
             temp = node.left # save the left child
             node = None # delete the current node
             return temp # return the left child to replace the deleted node

        #Node with two children, get the in-order successor
         node.info = lowestNode(node.right).info # find the in-order successor & copy its data to the current node
         node.right = deleteNode(node.right, node.info) # recursively delete the succesor value from the right subtree and update the root
    return node     


root = TreeNode(13)
node7 = TreeNode(7)
node15 = TreeNode(15)
node3 = TreeNode(3)
node8 = TreeNode(8)
node14 = TreeNode(14) 
node19 =TreeNode(19)
node18 = TreeNode(18)

root.left = node7
root.right = node15

node7.left = node3
node7.right = node8
    
node15.left = node14
node15.right = node19

node19.left = node18
deleteNode(root, 15)
insertNode(root, 10)
inOrderTrasversal(root,)
print("\n") 
result = search(root, 13)
if result:
    print(f"Found the node with value: {result.info}")
else:
    print("Value not found in the BST") 

print("\nLowest value:", lowestNode(root).info)       