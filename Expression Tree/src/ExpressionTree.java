import java.util.*;
public class ExpressionTree {

	public static final char SPACE = ' ';

	private TreeNode expressionRoot;

	public ExpressionTree() {
		expressionRoot = null;
	}

	public void constructTree (String s) {
		if (s == null || s.length() == 0) {
			throw new IllegalArgumentException();
		}
		Stack<TreeNode> st = new Stack<TreeNode>();
		char[] charArray = s.toCharArray();
		
		for (int i = 0; i < s.length(); i++) {
			if (charArray[i] == ' ') {
				continue;
			}
			TreeNode charNode = new TreeNode(charArray[i]);
			if (Character.isDigit(charArray[i])) {
				st.push(charNode);
			}
			else if (isOperator(charArray[i])) {
				TreeNode rightChild;
				TreeNode leftChild;
				try{
					rightChild = st.pop();
					leftChild = st.pop();
				}
				catch (EmptyStackException e){
					throw new IllegalArgumentException("Invalid postfix string");
				}
				charNode.left = leftChild;
				charNode.right = rightChild;
				st.push(charNode);
			}
		}
		if (st.size() > 1) {
			throw new IllegalArgumentException();
		}
		else {
			expressionRoot = st.pop();
		}
	}
	
	public String getPrefixExpression() {
		return getPrefixExpression(expressionRoot);
	}
	private String getPrefixExpression (TreeNode root) {
		if (root == null) {
			return "";
		}
		if (root.left != null && root.right != null) {
			return root.data + " " + getPrefixExpression(root.left) + " " + getPrefixExpression(root.right);
		}
		else {
			return root.data + "";
		}
	}
	
	public String getInfixExpression() {
		return getInfixExpression(expressionRoot);
	}
	private String getInfixExpression (TreeNode root) {
		if (root == null) {
			return "";
		}
		if (root.left == null && root.right == null) {
			return root.data + "";
		}
		else {
			return "(" + getInfixExpression(root.left) + " " + root.data + " " + getInfixExpression(root.right) + ")";
		}
	}
	
	public double evaluate() {
		return evaluate(expressionRoot);
	}
	private double evaluate(TreeNode root) {
		if (root == null ) {
			return 0;
		}
		if (root.left == null && root.right == null) {
			return Character.getNumericValue(root.data);
		}
		else {
			switch (root.data) {
			case '+':
				return evaluate(root.left) + evaluate(root.right);
			case '-':
				return evaluate(root.left) - evaluate(root.right);
			case '*':
				return evaluate(root.left) * evaluate(root.right);
			default:
				return evaluate(root.left) / evaluate(root.right);
			}
		}
	}

	/**
    * prints the tree sideways including indenting and connecters between nodes
    */
	public void printTree() {
	    if (expressionRoot == null) {
	        return;
	    }
        if (expressionRoot.right != null) {
            printTree(expressionRoot.right, true, "");
        }
        System.out.println(expressionRoot.data);
        if (expressionRoot.left != null) {
            printTree(expressionRoot.left, false, "");
        }
    }

    // use string and not stringbuffer on purpose as we need to change the indent at each recursion
    private void printTree(TreeNode root, boolean isRight, String indent){
        if (root.right != null) {
            printTree(root.right, true, indent + (isRight ? "        " : " |      "));
        }
        System.out.print(indent);
        if (isRight) {
            System.out.print(" /");
        } else {
            System.out.print(" \\");
        }
        System.out.print("----- ");
        System.out.println(root.data);
        if (root.left != null) {
            printTree(root.left, false, indent + (isRight ? " |      " : "        "));
        }
    }

	/**
	 * checks if char is a binary operator +, -, *, /
	 * 
	 * @param ch
	 *            - the char to check if it is an operator
	 * @return true if the specified character is one of the four basic binary
	 *         arithmetic operations
	 */
	private boolean isOperator(char ch) {
		return (ch == '+' || ch == '-' || ch == '*' || ch == '/');
	}

	/**
	 * TreeNode represents one node of an expression tree whose data is a
	 * character representing a binary operation or single digit data
	 */
	private static class TreeNode {
		// data fields
		private char data;
		private TreeNode left;
		private TreeNode right;

		// TODO Add two constructors
		public TreeNode(char data, TreeNode left, TreeNode right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}
		
		public TreeNode(char data) {
			this(data, null, null);
		}	
	}
}
