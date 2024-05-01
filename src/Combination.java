public class Combination {
    /**
     * [조합] : n개중 r개 뽑기
     *  - 주어진 요소의 모든 조합 만들기
     *  - 중복은 없어야 함(순열과 다름)
     *
     *  배열을 순회하면서 두가지 경우로 나눠서 조합
     *      1) 현재 인덱스를 선택하는 경우: visited = true
     *      2) 선택하지 않는 경우: visited = false
     */
    static int n;
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4};
        n = arr.length;
        boolean[] visited = new boolean[n];

        for (int i=0; i<=n; i++) {
            System.out.println(n+"개 중 "+i+"개 조합");
            combination(arr, visited, 0, n, i);
        }

    }
    public static void combination(int[] arr, boolean[] visited, int start, int n, int r) {
        if (r == 0) { // r개의 조합이 생성된 경우 print
            print(arr, visited);
            return;
        }
        for (int i=start; i<n; i++) { // start 인덱스부터 조합 만들기
            visited[i] = true; // 방문한 요소
            combination(arr, visited, i+1, n, r-1);
            visited[i] = false; // r개 조합이 만들어진 후에는 해당 요소의 방문 여부를 false로 변경
        }
    }

    public static void print(int[] arr, boolean[] visited) {
        for (int i=0; i<arr.length; i++) {
            if (visited[i]) {
                System.out.print(arr[i]+" ");
            }
        }
        System.out.println();
    }

}
