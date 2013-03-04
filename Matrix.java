
public class Matrix
{
  private boolean[][] values;
  private int rows, columns;
  
  public static void main(String[] args)
  {
    
    Matrix m = new Matrix(3,3);
    m.set(1,2, true);
    m.set(2,1, true); m.set(2,2, true); m.set(2,3, true);
    m.set(3,1, true);
    Matrix m2 = new Matrix(3,3);
    m2.set(1,2, true);
    m2.set(2,2, true); m2.set(2,3, true);
    m2.set(3,1, true); m2.set(3,2, true); m2.set(3,3, true);
    System.out.println("m1");
    System.out.println(m);
    System.out.println("m2");
    System.out.println(m2);
    Matrix m3 = m.or(m2);
    System.out.println("m3");
    System.out.println(m3);
    Matrix m4 = m.and(m2);
    System.out.println("m4");
    System.out.println(m4);
    
    
    Matrix m7 = m.compose(m2);
    System.out.println("m7");
    System.out.println(m7);
    
    
    /*
    Matrix m = new Matrix(3,3);
    m.set(1,2, true); m.set(2,1, true);
    Matrix m2 = new Matrix(2,2);
    m2.set(1,1, true); m2.set(2,2, true);
    System.out.println("m1");
    System.out.println(m);
    System.out.println("m2");
    System.out.println(m2);
    Matrix m3 = Matrix.compose(m, m2);
    System.out.println("m3");
    System.out.println(m3);
    System.out.println("^");
    System.out.println(m);
    Matrix m4 = m.warshall();
    System.out.println("m4\n"+m4);
    */
  }
  public Matrix(int i, int j)
  {
    values = new boolean[i][j];
    rows = i;
    columns = j;
  }
  
  public Matrix(Matrix m)
  {
    rows = m.rows();
    columns = m.columns();
    values = new boolean[rows][columns];
    for (int i = 1; i <= rows(); i++)
    {
      for (int j = 1; j <= columns(); j++)
      {
        set(i,j, m.get(i,j));
      }
    } 
  }
  
  public void set(int i, int j, boolean value)
  {
    check(i,j);
    values[i-1][j-1]=value;
  }
  
  public boolean get(int i, int j)
  {
    check(i,j);
    return values[i-1][j-1];
  }
  
  private void check(int i, int j)
  {
    if (i > rows || i < 1){throw new ArrayIndexOutOfBoundsException("Invalid row number");}
    if (j > columns || j < 1){throw new ArrayIndexOutOfBoundsException("Invalid column number");}
  }
    
  public int rows(){return rows;}
  public int columns(){return columns;}
  
  public String toString()
  {
    String s = "";
    for (int i = 0; i < rows; i++)
    {
      for (int j = 0; j < columns; j++)
      {
        s+= " "+(values[i][j] ? 1 : 0);
      }
      s+="\n";
    }
    return s;
  }
  
  public Matrix or(Matrix m)
  {
    Matrix n = new Matrix(m);
    for (int i = 1; i <= n.rows(); i++)
    {
      for (int j = 1; j <= n.columns(); j++)
      {
        n.set(i,j, get(i,j) || n.get(i,j));
      }
    }
    return n;
  }
  
  public Matrix and(Matrix m)
  {
    Matrix n = new Matrix(m);
    for (int i = 1; i <= n.rows(); i++)
    {
      for (int j = 1; j <= n.columns(); j++)
      {
        n.set(i,j, get(i,j) && n.get(i,j));
      }
    }
    return n;
  }
  
  public Matrix compose (Matrix m) {
    if (columns() != m.rows()) {
      throw new ArrayIndexOutOfBoundsException("Matrices must be AxN NxB, such that they have an equivalent number of rows and columns.");
    }
    
    Matrix result = new Matrix(rows(), m.columns());
    
    for (int i = 1; i <= rows(); i++) {
      for (int j = 1; j <= columns(); j++) {
        for (int k = 1; k <= columns(); k++) {
          if (get(i,k) && m.get(k,j)) {
            result.set(i, j, true);
            break;
          }
        }
      }
    }
    return result;
  }
  
  public Matrix warshall () {
    if (rows() != columns()) {
      throw new ArrayIndexOutOfBoundsException("Matrix must contain the same number of rows and columns.");
    }
    Matrix w = new Matrix(this);
    for (int k = 1; k <= w.rows(); k++) {
      for (int i = 1; i <= w.rows(); i++) {
        for (int j = 1; j <= w.rows(); j++) {
          w.set(i,j, w.get(i,j) || (w.get(i,k) && w.get(k,j)));
        }
      }
    }
    return w;
  }
}
