public class AVLTree {
    private class AVLNode {

        private Integer item;
        private AVLNode leftNode;
        private AVLNode rightNode;
        int height;
        int balanceFactor;

        public AVLNode(Integer item){
            this.item = item;
        }

        @Override
        public String toString(){
            return "Value: " + item;
        }
    }

    AVLNode rootNode;

    public void insert(Integer item){
        AVLNode node = createNewNode(item);

        rootNode = traverseToInsert(node, rootNode);
    }

    private AVLNode traverseToInsert(AVLNode newNode, AVLNode currentNode){
        if (currentNode == null)
            return newNode;

        int result = newNode.item.compareTo(currentNode.item);

        if (result >= 0)
            currentNode.rightNode = traverseToInsert(newNode, currentNode.rightNode);
         else if (result < 0)
            currentNode.leftNode = traverseToInsert(newNode, currentNode.leftNode);

        heightOfCurrentNode(currentNode);

        currentNode.balanceFactor = traverseToCalculateBalanceFactor(currentNode);

        if (isLeftHeavy(currentNode.balanceFactor))
            currentNode = rightRotate(currentNode);
         else if (isRightHeavy(currentNode.balanceFactor))
            currentNode = leftRotate(currentNode);

        return currentNode;
    }

    private int traverseToCalculateHeight(AVLNode currentNode) {
        if (currentNode == null)
            return 0;

        int left = 1 + traverseToCalculateHeight(currentNode.leftNode);
        int right = 1 + traverseToCalculateHeight(currentNode.rightNode);

        return left > right ? left : right;
    }

    private int traverseToCalculateBalanceFactor(AVLNode currentNode) {
        int left = 1 + traverseToCalculateHeight(currentNode.leftNode);
        int right = 1 + traverseToCalculateHeight(currentNode.rightNode);

        return left - right;
    }

    private void heightOfCurrentNode(AVLNode currentNode){
        currentNode.height = -1 + traverseToCalculateHeight(currentNode);
    }

    private boolean isLeftHeavy(int balanceFactor){
        return balanceFactor > 1;
    }

    private boolean isRightHeavy(int balanceFactor){
        return balanceFactor < -1;
    }

    private AVLNode leftRotate(AVLNode currentNode){
        if (currentNode.rightNode.balanceFactor == -1){
            if (currentNode.rightNode.leftNode != null){
                AVLNode tempNode = currentNode.rightNode.leftNode;
                currentNode.rightNode.leftNode = null;

                currentNode = leftRotate(currentNode);

                currentNode.leftNode.rightNode = tempNode;

                return currentNode;
            }

            currentNode.rightNode.leftNode = currentNode;
            currentNode = currentNode.rightNode;
            currentNode.leftNode.rightNode = null;

            return currentNode;
        }

        currentNode.rightNode = rightRotate(currentNode.rightNode);
        currentNode.rightNode.balanceFactor = traverseToCalculateBalanceFactor(currentNode.rightNode);

        return leftRotate(currentNode);
    }

    private AVLNode rightRotate(AVLNode currentNode){
        if (currentNode.leftNode.balanceFactor == 1){
            if (currentNode.leftNode.rightNode != null){
                AVLNode tempNode = currentNode.leftNode.rightNode;
                currentNode.leftNode.rightNode = null;

                currentNode = rightRotate(currentNode);

                currentNode.rightNode.leftNode = tempNode;

                return currentNode;
            }

            currentNode.leftNode.rightNode = currentNode;
            currentNode = currentNode.leftNode;
            currentNode.rightNode.leftNode = null;

            return currentNode;
        }

        currentNode.leftNode = leftRotate(currentNode.leftNode);
        currentNode.leftNode.balanceFactor = traverseToCalculateBalanceFactor(currentNode.leftNode);

        return rightRotate(currentNode);
    }

    private AVLNode createNewNode(Integer item){
        item = Math.abs(item);
        AVLNode node = new AVLNode(item);
        return node;
    }



}
