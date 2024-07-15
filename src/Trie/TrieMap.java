package Trie;

import java.util.HashMap;

public class TrieMap {
    private Node rootNode;
    public TrieMap() {
        // Trie 생성시 루트 노드는 기본으로 생성
        rootNode = new Node(); // 루트 노드는 빈 노드
    }
    // 트라이에 노드 추가
    public void insert(String word) {
        Node node = this.rootNode;
        // 단어의 문자를 순회하면서 자식 노드를 확인
        //  1) 자식 노드에 있는 문자라면 자식 노드를 가져옴
        //  2) 자식 노드에 없는 문자라면 새롭게 생성
        for (int i=0; i<word.length(); i++) node = node.childNode.computeIfAbsent(word.charAt(i), key -> new Node());
        node.endOfWord = true; // 단어(word)의 문자를 모두 순회하면 마지막 노드는 리프 노드(=마지막 문자)이기때문에 endOfWord를 true로 설정
    }
    // 문자가 포함되어 있는지 확인
    public boolean contains(String word) {
        Node node = this.rootNode;
        char c;
        for (int i=0; i<word.length(); i++) {
            c = word.charAt(i);
            if (!node.childNode.containsKey(c)) return false; // 현재 탐색하는 문자가 자식 노드에 없다면 존재하지 않는 단어
            node = node.childNode.get(c); // 마지막 문자를 node에 저장
        }
        return node.endOfWord; // 마지막 문자 여부를 반환
    }
    // 트라이에서 해당 단어 삭제
    public void delete(String word) {
        delete(this.rootNode, word, 0);
    }
    // 재귀 호출을 위한 함수 오버로딩
    private void delete(Node node, String word, int idx) {
        // 단어의 마지막 인덱스인 경우
        if (idx == word.length()) {
            if (!node.endOfWord) throw new Error(word + " not exist"); // 단어가 존재하지 않음
            node.endOfWord = false; // 단어의 끝 문자 표시를 false로 변경: 해당 문자 노드를 삭제하기 위함
            return; // 리프 노드의 부모 노드로 return
        }
        char c = word.charAt(idx); // 현재 확인해야하는 문자
        if (!node.childNode.containsKey(c)) throw new Error(word + " not exist"); // 자식 노드가 없는 경우(=단어가 존재하지 않는 경우)
        Node child = node.childNode.get(c); // 자식 노드 가져오기
        // 자식 노드가 있다면 리프 노드까지 내려가야 하므로 재귀 호출
        delete(child, word, idx+1);
        // 현 노드의 자식 노드가 리프 노드이고(=자식 노드가 없음), 단어의 끝인 문자가 아니라면 삭제
        if (child.childNode.isEmpty() && !child.endOfWord) node.childNode.remove(c, child);
    }

    private class Node {
        HashMap<Character, Node> childNode = new HashMap<>(); // 자식 노드
        boolean endOfWord; // 단어의 끝인지에 대한 플래그
    }
}


