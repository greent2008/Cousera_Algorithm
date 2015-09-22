/**
 * Created by jianengxi on 15-9-21.
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] grid;     //记录格子,包含首虚节点,尾虚节点 true代表open,false代表blocked
    private int rowNum;         //记录格子共有多少行
    private int colNum;         //记录格子共有多少列
    private WeightedQuickUnionUF ufs;   //并查集,判断首尾节点是否相连
    /*
    * backwash情况
    * 1 0 0 0 0
    * 1 0 0 0 0
    * 1 0 0 0 0
    * 1 0 1 0 0
    * 1 0 1 0 0
    * 水从高处往低处流
    * 第一列有水流过,但是第三列因为与虚拟尾部节点相通水也会倒灌第三列open的格子
    * */
    private WeightedQuickUnionUF backwash;  //考虑有backwash情况
    public Percolation(int N){
        if (N <= 0)
        {
            throw new IllegalArgumentException("N is out of bound");
        }
        rowNum = N;
        colNum = N;
        grid = new boolean[N*N];
        ufs = new WeightedQuickUnionUF(N*N+2);
        backwash = new WeightedQuickUnionUF(N*N+2);
        for (int i = 0; i < N*N; i++)
        {
            grid[i] = false;
        }
    }               // create N-by-N grid, with all sites blocked
    
    /*
    * 判断grid[i][j]是否合法
    * */
    private void validate(int i, int j){
        if (i < 1 || i > rowNum)
        {
            throw new IndexOutOfBoundsException("row index i out of Bounds");
        }
        if (j < 1 || j > colNum)
        {
            throw new IndexOutOfBoundsException("col index j out of Bounds");
        }
    }
    /*
    * 当open一个格子时
    * 检查它的四周的格子是否open*/
    private void check(int i, int j){
        int index = (i-1)*rowNum + j;
        if (rowNum == 1)
        {
            return;
        }
        int[] dx = {0,-1,0,1};
        int[] dy = {1,0,-1,0};
        for (int dir = 0; dir < 4; dir++)
        {
            int poX = j + dx[dir];
            int poY = i + dy[dir];
            if(1<=poX && poX <= colNum && 1<=poY && poY <= rowNum)
            {
                if (isOpen(poY,poX))
                {
                    ufs.union(index, (poY-1)*colNum + poX);
                    backwash.union(index, (poY-1)*colNum + poX);
                }
            }
        }
    }
    public void open(int i, int j){
        validate(i,j);
        int index = (i-1)*rowNum + j-1;
        grid[index] = true;
        /*      0
        ×    1  2  3  4
        ×    5  6  7  8
        ×    9 10 11 12
        ×   13 14 15 16
        ×       17
        ×首届点与第一行相连,尾节点与最后一行相连
        * */
        if (i == 1)
        {
            ufs.union(0,j);
            backwash.union(0, j);
        }
        if (i == rowNum)
        {
            ufs.union(rowNum*colNum+1,(i-1)*colNum+j);
        }
        check(i, j);
    }          // open site (row i, column j) if it is not open already
    public boolean isOpen(int i, int j){
        validate(i,j);
        int index = (i-1)*rowNum + j-1;
        return grid[index];
    }     // is site (row i, column j) open?
    public boolean isFull(int i, int j){
        validate(i,j);
        int index = (i-1)*colNum+j;
        return backwash.connected(0,index);
    }     // is site (row i, column j) full?
    public boolean percolates(){
        return ufs.connected(0,rowNum*colNum+1);
    }             // does the system percolate?
//    public static void main(String[] args){
        //int N = 4;
        //System.out.print(N);
        //Percolation test = new Percolation(N);
        //test.open(1,1);
        //test.open(1,2);
        //test.open(2,2);
        //test.open(3,2);
        //test.open(3,3);
        //test.open(4,3);
        //System.out.print(test.percolates());
//    }      // test client (optional)

}
