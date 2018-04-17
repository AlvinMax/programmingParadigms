public class BinarySearchSpan {
	static int x, n;
	static int[] a;

	public static void main(String[] args) {
		x = Integer.valueOf(args[0]);  
		n = args.length - 1; 
		a = new int[n]; 
		for (int i = 0 ; i < n ; ++i) {  
			a[i] = Integer.valueOf(args[i + 1]); 
		}

		int res1 = binarySearchWithoutRecursion(x);
		int res2 = binarySearchWithRecursion(0, n, x) - res1;

		System.out.println(res1 + " " + res2);
	}

	// pre: (0 < i < n : a[i - 1] >= a[i]) && 0 <= l <= r <= n 
	// post: (R == n && a[n - 1] >= x) || (R == 0 && x > a[0]) || (0 < R < n && a[R - 1] >= x && a[R] < x)
	// inv: (a' == a) && (0 < i < n : a[i - 1] >= a[i]) && 0 <= l <= r <= n && (l == 0 || x <= a[l - 1]) && (r == n || x > a[r]) && (r' - l') >= 2 * (r - l)
	public static int binarySearchWithRecursion(int l, int r, int x) {          
		if (l == r) {
			// inv && l == r  									  				
			return r; 
			// R = r
		}
		// inv 
		int mid = (l + r) / 2;
		// inv && mid == (l + r) / 2 
		if (a[mid] >= x) {  
			// inv && mid == (l + r) / 2  && a[mid] >= x
			return binarySearchWithRecursion(mid + 1, r, x);
		} else {
			// inv && mid == (l + r) / 2  && a[mid] < x
			return binarySearchWithRecursion(l , mid, x);
		}
	}

	// pre: (0 < i < n : a[i - 1] >= a[i])
	// post: (R == n && a[n - 1] > x) || (R == 0 && x >= a[0]) || (0 < R < n && a[R - 1] > x && a[R] <= x)
	public static int binarySearchWithoutRecursion(int x) {
		// (0 < i < n : a[i - 1] >= a[i])
		int l = 0, r = n;
		// (0 < i < n : a[i - 1] >= a[i]) && l == 0 && r == n
		// inv: (a' == a) && (0 < i < n : a[i - 1] >= a[i]) && 0 <= l <= r <= n && (l == 0 || x < a[l - 1]) && (r == n || x >= a[r]) && (r' - l') >= 2 * (r - l)
		while (l < r) {
			// inv && l < r 
			int mid = (l + r) / 2;
			// inv && l < r && mid == (l + r) / 2 
			if (a[mid] > x) {
				// inv && mid == (l + r) / 2  && a[mid] > x
				l = mid + 1;
				// inv && mid == (l + r) / 2  && a[mid] > x && l == mid + 1
			} else {
				// inv && mid == (l + r) / 2  && a[mid] <= x
				r = mid;
				// inv && mid == (l + r) / 2  && a[mid] <= x && r == mid
			}
			// inv && (r' - l') >= 2 * (r - l)
		}
		return r;
		//R = r		
	}
} 	