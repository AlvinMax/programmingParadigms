public class BinarySearch {
	static int x, n;
	static int[] a;
	public static void main(String[] args) {
		x = Integer.valueOf(args[0]);  
		n = args.length - 1; 
		if(n == 0) {
			System.out.println("0");
			return;
		}
		a = new int[n]; 
		for (int i = 0 ; i < n ; ++i) {  
			// inv: 0 <= i <= n - 1
			a[i] = Integer.valueOf(args[i + 1]); 
		}

		int res1 = BinarySearchWithoutRecursion();
		int res2 = BinarySearchWithRecursion(0, n);

		System.out.println(res2);
	}
	// n = a.size()
	// R = result
	// pre: (0 < i < n : a[i - 1] >= a[i]) && 0 <= l <= r <= n 
	// post: ((R < n && a[R] <= x) || (R == n && a[n - 1] > x)) || ((R == 0 && x >= a[0]) || (R > 0 && a[R - 1]  > x))
	public static int BinarySearchWithRecursion(int l, int r) {          
		if(l == r) {
			// pre: (0 < i < n : a[i - 1] >= a[i]) && l == r && 0 <= l = r <= n && (l == r == n || x >= a[r]) && (l == r == 0 || a[l] > x) 									  				
			return r; 
			// post: R = r
		}
		// pre: (0 < i < n : a[i - 1] >= a[i]) && 0 <= l < r <= n && (r == n || x >= a[r]) && (l == 0 || a[l] > x) 
		int mid = (l + r) / 2; 
		// pre: (0 < i < n : a[i - 1] >= a[i]) && 0 <= l < r <= n && (r == n || x >= a[r]) && (l == 0 || a[l] > x) && mid == (l + r) / 2 
		if(a[mid] > x) {  
			// pre: (0 < i < n : a[i - 1] >= a[i]) && 0 <= l < r <= n && (r == n || x >= a[r]) && (l == 0 || a[l] > x) && mid == (l + r) / 2  && a[mid] > x
			return BinarySearchWithRecursion(mid + 1, r);
		} else {
			// pre: (0 < i < n : a[i - 1] >= a[i]) && 0 <= l < r <= n && (r == n || x >= a[r]) && (l == 0 || a[l] > x) && mid == (l + r) / 2  && a[mid] <= x
			return BinarySearchWithRecursion(l , mid);
		}
		// post: (0 < i < n : a[i - 1] >= a[i]) && 0 <= l < r <= n && (r == n || x >= a[r]) && (l == 0 || a[l] > x) && (r - l) >= 2 * (r' - l')
	}
	// n = a.size()
	// R = result
	// pre: (0 < i < n : a[i - 1] >= a[i])
	// post: ((R < n && a[R] <= x) || (R == n && a[n - 1] > x)) || ((R == 0 && x >= a[0]) || (R > 0 && a[R - 1]  > x))
	public static int BinarySearchWithoutRecursion() {
		// pre: (0 < i < n : a[i - 1] >= a[i])
		int l = 0, r = n;
		// pre: (0 < i < n : a[i - 1] >= a[i]) && l == 0 && r == n
		// inv: (0 < i < n : a[i - 1] >= a[i]) && 0 <= l < r <= n && (r == n || x >= a[r]) && (l == 0 || a[l] > x) && (r - l) >= 2 * (r' - l')
		while(l < r) {
			//pre: (0 < i < n : a[i - 1] >= a[i]) && 0 <= l < r <= n && (r == n || x >= a[r]) && (l == 0 || a[l] > x)
			int mid = (l + r) / 2;
			// pre: (0 < i < n : a[i - 1] >= a[i]) && 0 <= l < r <= n && (r == n || x >= a[r]) && (l == 0 || a[l] > x) && mid == (l + r) / 2 
			if(a[mid] > x) {
				// pre: (0 < i < n : a[i - 1] >= a[i]) && 0 <= l < r <= n && (r == n || x >= a[r]) && (l == 0 || a[l] > x) && mid == (l + r) / 2  && a[mid] > x
				l = mid + 1;
				// post: (0 < i < n : a[i - 1] >= a[i]) && 0 <= l < r <= n && (r == n || x >= a[r]) && (l == 0 || a[l] > x) && mid == (l + r) / 2  && a[mid] > x && l == mid + 1
			} else {
				// pre: (0 < i < n : a[i - 1] >= a[i]) && 0 <= l < r <= n && (r == n || x >= a[r]) && (l == 0 || a[l] > x) && mid == (l + r) / 2  && a[mid] <= x
				r = mid;
				// post: (0 < i < n : a[i - 1] >= a[i]) && 0 <= l < r <= n && (r == n || x >= a[r]) && (l == 0 || a[l] > x) && mid == (l + r) / 2  && a[mid] <= x && r == mid
			}
			// post: (0 < i < n : a[i - 1] >= a[i]) && 0 <= l < r <= n && (r == n || x >= a[r]) && (l == 0 || a[l] > x) && (r - l) >= 2 * (r' - l')
		}
		return r;
		// post: R == r		
	}
} 	