package Trie;

public class TrieArray {
    static final int SIZE = 26; // 영어 소문자는 26자
    private Node rootNode;
    public TrieArray() {
        rootNode = new Node();
    }
    // 영어 소문자에 한하여 char를 int로 변경하는 메서드
    private int charToInt(char c){
        return c - 'a'; // 소문자만 있으므로 'a'를 빼면 된다.
    }
    // 트라이에 노드 추가
    public void insert(String word) {
        Node node = this.rootNode;
        // 단어의 문자를 순회하면서 자식 노드 확인
        for (int i=0; i < word.length(); i++) {
            char c = word.charAt(i);
            // 단어의 특정 문자를 인덱스(int)로 변환
            int intVal = charToInt(c);
            // 현 노드에 인덱스로 변환한 문자가 자식노드로 있는지 확인
            if (node.childNode[intVal] == null) {
                // 자식 노드에 없는 문자라면 새로 생성
                node.childNode[intVal] = new Node();
                node.childNode[intVal].val = c;
                node.childCnt++;
            }
            // 다음 탐색 노드를 자식노드로 변경
            node = node.childNode[intVal];
        }
        node.endOfWord = true; // 단어(word)의 문자를 모두 순회하면 마지막 노드는 리프 노드(=마지막 문자)이기때문에 endOfWord를 true로 설정
    }
    // 문자가 트라이에 포함되어 있는지 확인
    public boolean contains(String word) {
        Node node = this.rootNode;
        char c;
        for (int i=0; i<word.length(); i++) {
            c = word.charAt(i);
            int intVal = charToInt(c); // 문자를 인덱스로 변환
            if (node.childNode[intVal] == null) return false; // 현재 탐색하는 문자가 자식 노드에 없다면 존재하지 않는 단어
            node = node.childNode[intVal];
        }
        return node != null && node.endOfWord; // 마지막 문자 여부를 반환
    }
    // 트라이에서 해당 단어 삭제
    public void delete(String word) {
        delete(this.rootNode, word, 0);
    }
    // 재귀 호출을 위한 함수 오버로딩
    public void delete(Node node, String word, int idx) {
        // 단어의 마지막인 경우
        if (idx == word.length()) {
            if (!node.endOfWord) throw new Error(word + " not exist"); // 단어가 존재하지 않음
            node.endOfWord = false; // 단어의 끝 문자 표시를 false로 변경: 해당 문자 노드를 삭제하기 위함
            return; // 리프 노드의 부모 노드로 return
        } else {
            if (node.childCnt == 0) throw new Error(word + " not exist"); // 자식 노드가 없는 경우(=단어가 존재하지 않는 경우)
        }

        char c = word.charAt(idx); // 현재 확인해야하는 문자
        int intVal = charToInt(c); // 문자를 인덱스로 변환

        Node child = node.childNode[intVal]; // 자식 노드 가져오기
        // 자식 노드가 있다면 리프 노드까지 내려가야 하므로 재귀 호출
        delete(child, word, idx+1);
        // 현 노드의 자식 노드가 삭제되었다면, 현 노드의 자식 노드 수 1 차감
        if (node.childNode[intVal] == null) node.childCnt--;
        // 현 노드의 자식 노드가 리프 노드이고(=자식 노드가 없음), 단어의 끝인 문자가 아니라면 삭제
        if (child.childCnt == 0 && !node.endOfWord) node = null;
    }

    private class Node {
        Node[] childNode = new Node[SIZE]; // 자식 노드: 영어 소문자를 인덱스화 하여 저장
        boolean endOfWord; // 단어의 끝인지에 대한 플래그
        int childCnt = 0; // 자식 노드 수
        char val; // 노드의 문자
    }
}
