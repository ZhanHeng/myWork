
import java.util.*;
class Node{
	int l,r;
	int mx;
	public Node(int l, int r, int mx) {
		super();
		this.l = l;
		this.r = r;
		this.mx = mx;
	}
}
public class Main {
	public static Node [] segTree;
	public static int [] array;
	public static Scanner input ;
	// 构造线段树
	public static void buildSegmentTree(int begin ,int end ,int id){
		segTree[id].l=begin;
		segTree[id].r=end;
		if(begin==end){
			segTree[id].mx = array[begin]; /* 只有一个元素,节点记录该单元素 */ 
		}else{
			//递归构建左右子树
			 int mid = (begin + end) / 2;
			buildSegmentTree(begin,mid,id * 2);
			buildSegmentTree(mid +1,end,id * 2 +1);
			//回溯时 得到当前node节点的线段信息
			segTree[id].mx= Math.max(segTree[id * 2].mx, segTree[id * 2 +1].mx);
		}
	}
	// 区间更新
	// 其中P为要更新的节点，add为需要更改的值，begin,end为当前节点存储的区间，node为当前节点
	public static void update(int p ,int add,int begin ,int end ,int id){
		if(begin==end){
			segTree[id].mx+=add;
			return;
		}
		int m = (begin + end)/2;
		if(p<=m)
			update(p,add,begin,m,id * 2);
		else
			update(p,add,m+1,end,id * 2 +1);
		segTree[id].mx= segTree[id * 2].mx +segTree[id * 2 +1].mx;
	}
	// 区间查询
	//（其中node为当前查询节点，begin,end为当前节点存储的区间，left,right为此次query从大到小）
	public static int query(int id , int L ,int R){
		if(segTree[id].r<L || segTree[id].l>R) return 0;
		if (segTree[id].l>=L && segTree[id].r<=R)    
		        return segTree[id].mx; 
		return Math.max(query(id*2, L , R ),query(id*2 + 1, L , R ));
	}
	public static void main(String[] args) {
		input= new Scanner(System.in);
		while (input.hasNextInt()) {
			int n = input.nextInt(); // 机票的总数
			int m = input.nextInt(); // 询问的总数
			array= new int[100010];
			segTree= new Node[400010];
			for (int i = 0; i < 400010; i++) {
				Node node =new Node(0, 0, 0);
				segTree[i]=node;
			}
			for (int i = 0; i < n; i++) {
				int t = input.nextInt(); // 时间
				int v = input.nextInt(); // 价格
				array[t]=v;
			}
			buildSegmentTree(1,100000, 1);
			for (int i = 0; i < m; i++) {
				int a = input.nextInt(); // 询问的时间区间
				int b = input.nextInt(); 
				int res = query(1,a,b);
				if(res==0){
					System.out.println("None");
				}else
					System.out.println(res);
			}
				
		}
	}
}
