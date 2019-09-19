import com.toberge.data.Stack;

public class ExpressionTree implements Solvable {

    private ExpressionTreeNode root = null;

    public ExpressionTree() {

    }

    public ExpressionTree(ExpressionTreeNode root) {
        this.root = root;
    }

    public ExpressionTree(String expression) { // in reverse polish notation
        String[] exprArr = expression.split(" ");
        Stack<OperandNode> stack = new Stack<>();
        OperatorNode currentRoot = null;
        OperatorNode additionalOperator = null;

        for (String s : exprArr) {
            System.out.println(s);
            if (s.length() == 1 && OperatorNode.isOperator(s.charAt(0))) {
                currentRoot = new OperatorNode(s.charAt(0));
                if (!stack.isEmpty()) {
                    currentRoot.setRightChild(stack.pop()); // add one operand
                } else if (additionalOperator != null) { // TODO this "solution" does not work.
                    currentRoot.setRightChild(additionalOperator);
                    additionalOperator = null;
                } else {
                    additionalOperator = new OperatorNode(s.charAt(0));
                }

                if (additionalOperator == null) { // TODO yep, gonna be wrong
                    if (root != null) {
                        currentRoot.setLeftChild(root); // add expression as child
                    } else {
                        if (!stack.isEmpty()) {
                            currentRoot.setLeftChild(stack.pop()); // add next operand in stack
                        }
                    }
                    root = currentRoot; // set new root
                }
                System.out.println(currentRoot + " and " + additionalOperator);
            } else {
                try {
                    stack.push(new OperandNode(Integer.parseInt(s))); // add number to stack
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    throw new IllegalArgumentException("Invalid expression, must only contain numbers, operators and spaces.");
                }
            }
        }
    }

    public ExpressionTreeNode getRoot() {
        return root;
    }

    public void setRoot(ExpressionTreeNode root) {
        this.root = root;
    }

    @Override
    public double solve() {
        return root.solve();
    }

    @Override
    public String toString() {
        return root.toString().replaceAll("^\\(", "").replaceAll("\\)$", "");
    }


    private static void demo1() {
        OperandNode two = new OperandNode(2);
        OperandNode three = new OperandNode(3);
        OperandNode four = new OperandNode(4);
        OperandNode ten = new OperandNode(10);
        OperatorNode twoplusfour = new OperatorNode('+', two, four);
        // (2+4)*10/3 is 2 4 + 10 * 3 /
        OperatorNode timesten = new OperatorNode('*', twoplusfour, ten);
        OperatorNode divthree = new OperatorNode('/', timesten, three);
        ExpressionTree tree = new ExpressionTree(divthree);
        System.out.println(tree + " = " + tree.solve());
    }

    private static void demo2() {
        OperandNode three = new OperandNode(3);
        OperandNode four = new OperandNode(4);
        OperandNode five = new OperandNode(5);

        OperandNode nine = new OperandNode(9);
        OperandNode four2 = new OperandNode(4);

        // (3*4+5) / (4+9) becomes 4 5 + 3 * / 4 9 +
        OperatorNode fourtimesthree = new OperatorNode('*', four, three);
        OperatorNode plusfive = new OperatorNode('+', fourtimesthree, five);
        OperatorNode fourplusnine = new OperatorNode('+', four2, nine);
        OperatorNode divthree = new OperatorNode('/', plusfive, fourplusnine);
        ExpressionTree tree = new ExpressionTree(divthree);
        System.out.println(tree + " = " + tree.solve());
    }

    private static void demoDrawn() {
        OperandNode eighteen = new OperandNode(18);
        OperandNode ten = new OperandNode(10);
        OperandNode two = new OperandNode(2);
        OperandNode three = new OperandNode(3);

        OperandNode fourtyeight = new OperandNode(48);
        OperandNode four = new OperandNode(4);
        OperandNode six = new OperandNode(6);
        OperandNode nine = new OperandNode(9);

        OperatorNode twoplusthree = new OperatorNode('+', two, three);
        OperatorNode tendiv = new OperatorNode('/', ten, twoplusthree);
        OperatorNode eighteenminus = new OperatorNode('-', eighteen, tendiv);

        OperatorNode sixminusnine = new OperatorNode('-', six, nine);
        OperatorNode fourtimes = new OperatorNode('*', four, sixminusnine);
        OperatorNode fourtyeightdiv = new OperatorNode('/', fourtyeight, fourtimes);

        OperatorNode times = new OperatorNode('*', eighteenminus, fourtyeightdiv);

        ExpressionTree tree = new ExpressionTree(times);
        System.out.println(tree + " = " + tree.solve());
    }

    public static void main(String[] args) {
        demo1();
        demo2();
        demoDrawn();

        /*ExpressionTree tree = new ExpressionTree("2 4 + 10 * 3 /");
        System.out.println(tree.toString());
        System.out.println(tree.solve());
        // (3*4+5) / (4+9)
        tree = new ExpressionTree("4 5 + 3 * / 4 9 +");
        System.out.println(tree.toString());
        System.out.println(tree.solve());*/
    }
}

interface Solvable {
    double solve();
}

class TreeNode {

    private TreeNode leftChild = null;
    private TreeNode rightChild = null;

    public TreeNode() {

    }

    public TreeNode(TreeNode leftChild, TreeNode rightChild) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public void snap() {
        leftChild = null;
        rightChild = null;
    }

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }


    // It is absolutely crucial that we add FROM THE RIGHT
    // reverse polish notation is our deal
    // thus I won't let this be used (gotta be explicit)
    public void addChildFromRight(TreeNode node) {
        if (rightChild == null) {
            rightChild = node;
        } else if (leftChild == null) {
            leftChild = node;
        }
    }

    public void appendLeft(TreeNode node) {
        if (leftChild != null) {
            leftChild.appendLeft(node);
        } else {
            leftChild = node;
        }
    }

    public void appendRight(TreeNode node) {
        if (rightChild != null) {
            rightChild.appendRight(node);
        } else {
            rightChild = node;
        }
    }
}

abstract class ExpressionTreeNode extends TreeNode implements Solvable {

    public ExpressionTreeNode() {
        super();
    }

    public ExpressionTreeNode(ExpressionTreeNode leftChild, ExpressionTreeNode rightChild) {
        super(leftChild, rightChild);
    }

    abstract String asString();

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (getLeftChild() != null) {
            builder.append("(");
            builder.append(getLeftChild().toString());
            builder.append(" ");
        }
        builder.append(this.asString());
        if (getRightChild() != null) {
            builder.append(" ");
            builder.append(getRightChild().toString());
            builder.append(")");
        }

        return builder.toString();
    }

}

class OperandNode extends ExpressionTreeNode/*TreeNode implements Solvable */{

    private double number;

    public OperandNode(double number) {
        this.number = number;
    }

    @Override
    public double solve() {
        return number;
    }

    @Override
    public String asString() {
        return "" + number;
    }
}

class OperatorNode extends ExpressionTreeNode/*TreeNode implements Solvable*/ {

    private char operator;

    public OperatorNode(char operator) {
        validate(operator);
        this.operator = operator;
    }

    public OperatorNode(char operator, ExpressionTreeNode leftChild, ExpressionTreeNode rightChild) {
        super(leftChild, rightChild);
        validate(operator);
        this.operator = operator;
    }

    private void validate(char operator) {
        if (operator != '*' && operator != '/' && operator != '+' && operator != '-') {
            throw new IllegalArgumentException("Invalid operator");
        }
    }

    public static boolean isOperator(char operator) {
        return operator == '*' || operator == '/' || operator == '+' || operator == '-';
    }

    /**
     * ahem,
     */
    @Override
    public double solve() {
        switch (operator) {
            case '+':
                return
                    ((Solvable) getLeftChild()).solve()
                        +
                    ((Solvable) getRightChild()).solve();
            case '-':
                return
                    ((Solvable) getLeftChild()).solve()
                        -
                    ((Solvable) getRightChild()).solve();
            case '*':
                return
                    ((Solvable) getLeftChild()).solve()
                        *
                    ((Solvable) getRightChild()).solve();
            case '/':
                return
                    ((Solvable) getLeftChild()).solve()
                        /
                    ((Solvable) getRightChild()).solve();
            default:
                throw new IllegalStateException("Invalid operator");
        }
    }

    @Override
    public String asString() {
        return "" + operator;
    }
}