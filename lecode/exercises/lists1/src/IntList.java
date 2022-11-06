public class IntList {
	public int first;
	public IntList rest;

	public IntList(int f, IntList r) {
		first = f;
		rest = r;
	}

	/** Return the size of the list using... recursion! */
	public int size() {
		if (this.rest == null){
			return 1;
		}
		return 1 + this.rest.size();
	}

	/** Return the size of the list using no recursion! */
	public int iterativeSize() {
		int size = 0;
		IntList p = this;
		while (p != null){
			size +=1;
			p = p.rest;
		}
		return size;
	}

	/** Returns the ith value in this list.*/
	public int get(int i) {
		if (i > this.size()){
			return 0;
		}
		if (i == 1){
			return first;
		}
		else{
			return this.rest.get(i-1);
		}
	}

	public static IntList incrList(IntList L, int x) {
		if (L.rest == null){
			return new IntList(L.first + x, null);
		}
		return new IntList(L.first + x, incrList(L.rest, x));
	}

	/** Returns an IntList identical to L, but with
	 * each element incremented by x. Not allowed to use
	 * the 'new' keyword. */
	public static IntList dincrList(IntList L, int x) {
		L.first = L.first + x;
		if (L.rest == null){
			return L;
		}
		return dincrList(L.rest, x);
	}

	public static void main(String[] args) {
		IntList L = new IntList(15, null);
		L = new IntList(10, L);
		L = new IntList(5, L);

		System.out.println(L.iterativeSize());
		System.out.println(L.get(1));
		System.out.println(incrList(L, 3));
		System.out.println(dincrList(L, 3));
	}
} 