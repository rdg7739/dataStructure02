package proj2;

import java.util.ArrayList;

// BinarySearchTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
{
	/**
	 * Construct the tree.
	 */
	public BinarySearchTree( )
	{
		root = null;
	}

	/**
	 * Insert into the tree; duplicates are ignored.
	 * @param x the item to insert.
	 * @throws UnderflowException throw when the node is duplicate
	 */
	public void insert( AnyType x ) throws WrongNodeException
	{
		if(contains(x))
			throw new WrongNodeException("Duplicate exception");
		root = insert( x, root );
		this.totalNumNode++;
	}

	/**
	 * Remove from the tree. Nothing is done if x is not found.
	 * @param x the item to remove.
	 * @throws UnderflowException throws when the node is not exist in the tree
	 */
	public void remove( AnyType x ) throws WrongNodeException
	{
		if(!contains(x))
			throw new WrongNodeException("Non exist");
		root = remove( x, root );
		this.totalNumNode--;
	}

	/**
	 * Find the smallest item in the tree.
	 * @return smallest item or null if empty.
	 * @throws UnderflowException throws when the tree is empty
	 */
	public AnyType findMin( ) throws UnderflowException
	{
		if( isEmpty() )
			throw new UnderflowException("Tree is empty");
		return findMin( root ).element;
	}
	/**
	 * find nth element and while doing that set the right rank number
	 * @param n the number of order in the tree 
	 * @return nth element
	 * @throws UnderflowException throws when the tree is empty
	 * 							  throws when the tree has less than total number of node.
	 */
	public AnyType nthElement(int n) throws UnderflowException{
		if( isEmpty( ) )
			throw new UnderflowException("Tree is empty");
		if(n >= this.totalNumNode){
			throw new UnderflowException("node in the tree is less than " + n);
		}
		z = 0; //initialize z to zero
		setRank(root);
		return nthElement(root, n);
	}
	/**
	 * return the Strings of info
	 * @param nrlvl number of lvl that user want to print from 0 to nrlvl
	 *
	 */
	public String toString(int nrLvl) throws UnderflowException {
	    String hoo = "";
	    if(isEmpty())
		throw new UnderflowException("Tree is empty");
	    for(int i = 0; i < nrLvl; i++){
		hoo+= "Level " + i + "\n " + toStrings(i);
	    }
	    return hoo;
	}
	/**
	 * find the rank of the element
	 * @param x the element that want to find rank of it
	 * @return the rank
	 * @throws WrongNodeException if there is no such node
	 * @throws UnderflowException if tree is empty
	 */
	public int rank( AnyType x ) throws WrongNodeException, UnderflowException  {
		if( isEmpty( ) )
			throw new UnderflowException("Tree is Empty");
		return findRank(x, root);// The rank of an element is its position (starting with 1) in an in-order traversal.
	}
	/**
	 * find the median node in the tree
	 * @return the median node of the tree
	 * @throws UnderflowException if the tree is empty
	 */
	public AnyType median( ) throws UnderflowException {
		if (this.totalNumNode % 2 == 0){
			return nthElement(this.totalNumNode / 2);
		}
		else{
			return nthElement((this.totalNumNode+1)/2); 
		}
		//2^(h+1)-1 = # of node in perfect 
		//if 2^(h+1)-1 is even
		//(2^(h+1)-1)/2 )th node
		// if "2^(h+1)-1 is odd +1 and / 2 th" node
	}
	/**
	 * find that tree is perfect tree or not
	 * @return true if the tree is perfect
	 */
	public boolean isPerfect( ){
		int treeSize = (int)Math.pow(2,(height(root) + 1))-1;
		if(this.totalNumNode+PREVENT_OUTOFBOUNCE == treeSize)		//2^(h+1)-1 = # of node in perfect 
			return true; //if the BST is a perfect binary tree.
		return false;
	}
	/**
	 * find the tree is complete tree or not
	 * @return true if the tree is complete tree
	 */
	public boolean isComplete( ){
		if(isPerfect())
			return true; //if the BST is a perfect binary tree then it is complete, too.
		return false;
	}
	/**
	 * return the info if node (node's element, size of tree, root element) is on specific lvl
	 * @param nrLevels is the number of level that user want to find
	 * @return the info of node in such level
	 * @throws UnderflowException throws when no nodes on specific lvl
	 */
	private String toStrings( int nrLevels ) throws UnderflowException{
		String hoo = "";
		int k = 0; //tracking the amount of node that will print
		if(nrLevels == 0){
			return "( " + root.element + " , " + root.getTreeSize() + " , " + null + ")\n";
		}
		ArrayList<BinaryNode<AnyType>> foo = treeInfo(root, nrLevels, START);
		for(int i = 0; i < foo.size(); i++){
			if(foo.get(i).left != null){
				k++;
				if(k%6 == 0 && k != 0)
					hoo+= "\n";
				hoo += "( " + foo.get(i).left.element + " , " + foo.get(i).left.getTreeSize() + " , " + foo.get(i).element + ")";
			}
			
			if(foo.get(i).right != null){
				k++;
				if(k%6 == 0 && k != 0)
					hoo+= "\n";
				hoo += "( " + foo.get(i).right.element + " , " + foo.get(i).right.getTreeSize() + " , " + foo.get(i).element+ ")";
			}
		}
		if (hoo.equals("")){
			throw new UnderflowException("Tree does not have deeper level");
		}
		return hoo+= "\n";
		//-- generates the level-order output described in the sample output below.
	}

	/**
	 * Find the largest item in the tree.
	 * @return the largest item of null if empty.
	 * @throws UnderflowException throws when the tree is empty
	 */
	public AnyType findMax( ) throws UnderflowException
	{
		if( isEmpty( ) )
			throw new UnderflowException( );
		return findMax( root ).element;
	}

	/**
	 * Find an item in the tree.
	 * @param x the item to search for.
	 * @return true if not found.
	 */
	public boolean contains( AnyType x )
	{
		return contains( x, root );
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty( )
	{
		root = null;
	}

	/**
	 * Test if the tree is logically empty.
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty( )
	{
		return root == null;
	}

	/**
	 * Print the tree contents in sorted order.
	 * @throws UnderflowException 
	 */
	public String printTree( ) throws UnderflowException
	{
		if( isEmpty( ) )
			throw new UnderflowException( "Empty tree" );
		else
			return printTree( root );
	}

	/**
	 * Internal method to insert into a subtree. increase the size of t whenever the node entered underneath of it
	 * @param x the item to insert.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t )
	{
		if( t == null )
			return new BinaryNode<AnyType>( x, null, null );
		int compareResult = x.compareTo( t.element );
		if( compareResult < 0 ){
			t.incTreeSize();
			t.left = insert( x, t.left );
		}
		else if( compareResult > 0 ){
			t.incTreeSize();
			t.right = insert( x, t.right );
		}
		else
			;  // Duplicate; do nothing
		return t;
	}
	/**
	 * reset the rank of the node in the tree after remove or insert node
	 * @param t - the root
	 * @return the element of t
	 */
	private AnyType setRank(BinaryNode<AnyType> t){
		AnyType element = null;
		if(t == null){
			return element;
		}
		if(t.left != null && element == null)
			element = setRank(t.left);

		t.setRank(++z);
		if(z == this.totalNumNode)
			return t.element;
		if(t.right != null){
			element = setRank(t.right);
		}
		return element;

	}
	/**
	 * find the nth element in the tree
	 * @param t - the node that roots the subtree.
	 * @param n - the number of node that user want to find
	 * @return the element of node
	 */
	private AnyType nthElement(BinaryNode<AnyType> t, int n){
		AnyType foo = null;
		if(t!= null && foo == null){
			foo = nthElement(t.left, n);
			if(t.getRank() == n){
				return   t.element;
			}
			if(foo == null)
				foo = nthElement(t.right, n);
		}
		return foo;
	}
	/**
	 * Internal method to remove from a subtree. decrease the t's tree size whenever the node underneath is removed 
	 * @param x the item to remove.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<AnyType> remove( AnyType x, BinaryNode<AnyType> t )
	{
		if( t == null )
			return t;   // Item not found; do nothing

		int compareResult = x.compareTo( t.element );
		t.decTreeSize();
		if( compareResult < 0 ){
			t.left = remove( x, t.left );
		}
		else if( compareResult > 0 ){
			t.right = remove( x, t.right );
		}
		else if( t.left != null && t.right != null ) // Two children
		{
			t.element = findMin( t.right ).element;
			t.right = remove( t.element, t.right );
		}
		else
			t = ( t.left != null ) ? t.left : t.right;
		return t;
	}
	/**
	 * find a rank of node 
	 * @param x the node that user want to find rank of it
	 * @param t  the node that roots the subtree.
	 * @return the rank of x 
	 * @throws WrongNodeException throws when there are no such node in the tree
	 * @throws UnderflowException if the node is empty
	 */
	private int findRank(AnyType x,  BinaryNode<AnyType> t ) throws WrongNodeException, UnderflowException
	{
		if( t == null )
			throw new WrongNodeException("There is no such node");
		nthElement(this.totalNumNode+PREVENT_OUTOFBOUNCE);
		int compareResult = x.compareTo( t.element );

		if( compareResult < 0 )
			return findRank( x, t.left );
		else if( compareResult > 0 )
			return findRank( x, t.right );
		else
			return t.getRank();    // Match
	}

	/**
	 * Internal method to find the smallest item in a subtree.
	 * @param t the node that roots the subtree.
	 * @return node containing the smallest item.
	 */
	private BinaryNode<AnyType> findMin( BinaryNode<AnyType> t )
	{
		if( t == null )
			return null;
		else if( t.left == null )
			return t;
		return findMin( t.left );
	}

	/**
	 * Internal method to find the largest item in a subtree.
	 * @param t the node that roots the subtree.
	 * @return node containing the largest item.
	 */
	private BinaryNode<AnyType> findMax( BinaryNode<AnyType> t )
	{
		if( t != null )
			while( t.right != null )
				t = t.right;

		return t;
	}

	/**
	 * Internal method to find an item in a subtree.
	 * @param x is item to search for.
	 * @param t the node that roots the subtree.
	 * @return node containing the matched item.
	 */
	private boolean contains( AnyType x, BinaryNode<AnyType> t )
	{
		if( t == null )
			return false;

		int compareResult = x.compareTo( t.element );

		if( compareResult < 0 )
			return contains( x, t.left );
		else if( compareResult > 0 )
			return contains( x, t.right );
		else
			return true;    // Match
	}

	/**
	 * Internal method to print a subtree in sorted order.
	 * @param t the node that roots the subtree.
	 * @return the list of node 
	 */
	private String printTree( BinaryNode<AnyType> t )
	{
		String hoo = "";
		if( t != null )
		{
			hoo += printTree( t.left );
			hoo +=  t.element + "\n";
			hoo += printTree( t.right );
		}
		return hoo;
	}
	/**
	 * put the nodes in the list at such level
	 * @param t  the node that roots the subtree.
	 * @param level that user want to get the node
	 * @param currentLvl track the current level of node
	 * @return the list of node at level
	 */
	private ArrayList<BinaryNode<AnyType>> treeInfo( BinaryNode<AnyType> t, int level , int currentLvl)
	{
		ArrayList<BinaryNode<AnyType>> foo = new ArrayList<BinaryNode<AnyType>>();
		if( t != null )
		{
			if(currentLvl == level)
				foo.add(t);
			if(currentLvl < level){
				foo.addAll(treeInfo( t.left , level, ++currentLvl));
				--currentLvl;
				foo.addAll(treeInfo( t.right , level, ++currentLvl));
				--currentLvl;
			}
		}
		return foo;
	}

	/**
	 * Internal method to compute height of a subtree.
	 * @param t the node that roots the subtree.
	 */
	private int height( BinaryNode<AnyType> t )
	{
		if( t == null )
			return -1;
		else
			return 1 + Math.max( height( t.left ), height( t.right ) );    
	}

	// Basic node stored in unbalanced binary search trees
	private static class BinaryNode<AnyType>
	{
		// Constructors
		BinaryNode( AnyType theElement )
		{
			this( theElement, null, null );
		}
		/**
		 * constructor
		 * @param theElement - the element of node
		 * @param lt - left node
		 * @param rt - right node
		 */
		BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt )
		{
			element  = theElement;
			left     = lt;
			right    = rt;
			treeSize = 1;

		}
		private int treeSize;
		private int rank;
		AnyType element;            // The data in the node
		BinaryNode<AnyType> left;   // Left child
		BinaryNode<AnyType> right;  // Right child
		/**
		 * get the size of tree
		 * @return the tree size
		 */
		public int getTreeSize() {
			return treeSize;
		}
		/**
		 * get the rank of the node
		 * @return rank of the node
		 */
		public int getRank() {
			return rank;
		}
		/**
		 * set the rank of node
		 * @param rank of node
		 */
		public void setRank(int rank) {
			this.rank = rank;
		}
		/**
		 * increase the size of tree by one
		 */
		public void incTreeSize() {
			++treeSize;
		}
		/**
		 * decrease the size of tree by one
		 */
		public void decTreeSize() {
			--treeSize;
		}
		/**
		 * return the element of node
		 */
		public String toString(){
			String hoo = ""+ this.element;
			return hoo;
		}
	}


	/** The tree root. */
	private BinaryNode<AnyType> root; 
	private final int START = 1; //starter number
	private final int PREVENT_OUTOFBOUNCE = -1; // use for decreasing the totlaNumNode by one to prevent out of bounce exception
	private int totalNumNode = 1; //the total number of node + 1 
	private static int z = 1; //used to keep track the order of node.
	// Test program
	public static void main( String [ ] args )
	{
		try {
			BinarySearchTree<Integer> t = new BinarySearchTree<Integer>( );
			final int NUMS = 4000;
			final int GAP  =   37;

			System.out.println( "Checking... (no more output means success)" );

			for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
				t.insert( i );

			for( int i = 1; i < NUMS; i+= 2 )
				t.remove( i );

			if( NUMS < 40 )
				t.printTree( );
			if( t.findMin( ) != 2 || t.findMax( ) != NUMS - 2 )
				System.out.println( "FindMin or FindMax error!" );

			for( int i = 2; i < NUMS; i+=2 )
				if( !t.contains( i ) )
					System.out.println( "Find error1!" );

			for( int i = 1; i < NUMS; i+=2 )
			{
				if( t.contains( i ) )
					System.out.println( "Find error2!" );
			}
		}catch (UnderflowException e) {
			System.out.println(e.toString());
		}catch (WrongNodeException e) {
			System.out.println(e.toString());
		}
	}
}
