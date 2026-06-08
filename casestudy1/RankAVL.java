package casestudy1;

class RankNode {
    int key;
    RankNode left, right;
    int height;
    int size;

    RankNode(int key) {
        this.key = key;
        this.height = 1;
        this.size = 1;
    }
}

public class RankAVL {

    // ---------- Utility Functions ----------
    static int height(RankNode n) {
        return n == null ? 0 : n.height;
    }

    static int size(RankNode n) {
        return n == null ? 0 : n.size;
    }

    static void update(RankNode n) {
        if (n != null) {
            n.height = 1 + Math.max(height(n.left), height(n.right));
            n.size = 1 + size(n.left) + size(n.right);
        }
    }

    static int balanceFactor(RankNode n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    // ---------- Rotations ----------
    static RankNode rightRotate(RankNode y) {
        RankNode x = y.left;
        RankNode T2 = x.right;

        x.right = y;
        y.left = T2;

        update(y);
        update(x);

        return x;
    }

    static RankNode leftRotate(RankNode x) {
        RankNode y = x.right;
        RankNode T2 = y.left;

        y.left = x;
        x.right = T2;

        update(x);
        update(y);

        return y;
    }

    // ---------- INSERT ----------
    static RankNode insert(RankNode root, int key) {
        if (root == null)
            return new RankNode(key);

        if (key > root.key) {
            root.left = insert(root.left, key); // DESC order
        } else if (key < root.key) {
            root.right = insert(root.right, key);
        } else {
            return root;
        }

        update(root);

        int bf = balanceFactor(root);

        // LL
        if (bf > 1 && key > root.left.key)
            return rightRotate(root);

        // LR
        if (bf > 1 && key < root.left.key) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // RR
        if (bf < -1 && key < root.right.key)
            return leftRotate(root);

        // RL
        if (bf < -1 && key > root.right.key) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // ---------- GET MIN VALUE ----------
    static RankNode minValueNode(RankNode node) {
        RankNode current = node;
        while (current.right != null)
            current = current.right;
        return current;
    }

    // ---------- DELETE ----------
    static RankNode delete(RankNode root, int key) {
        if (root == null)
            return null;

        if (key > root.key) {
            root.left = delete(root.left, key);
        } else if (key < root.key) {
            root.right = delete(root.right, key);
        } else {

            if (root.left == null || root.right == null) {
                RankNode temp = (root.left != null) ? root.left : root.right;

                if (temp == null)
                    return null;
                else
                    return temp;
            } else {
                RankNode temp = minValueNode(root.left);
                root.key = temp.key;
                root.left = delete(root.left, temp.key);
            }
        }

        update(root);

        int bf = balanceFactor(root);

        // LL
        if (bf > 1 && balanceFactor(root.left) >= 0)
            return rightRotate(root);

        // LR
        if (bf > 1 && balanceFactor(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // RR
        if (bf < -1 && balanceFactor(root.right) <= 0)
            return leftRotate(root);

        // RL
        if (bf < -1 && balanceFactor(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // ---------- RANK QUERY ----------
    static int rankOf(RankNode root, int key) {
        int rank = 1; // highest score = rank 1

        while (root != null) {

            if (key > root.key) {
                root = root.left;
            } else if (key < root.key) {
                rank += size(root.left) + 1;
                root = root.right;
            } else {
                rank += size(root.left);
                break;
            }
        }

        return rank;
    }

    // ---------- SCORE UPDATE ----------
    static RankNode updateScore(RankNode root, int oldScore, int newScore) {
        root = delete(root, oldScore);
        root = insert(root, newScore);
        return root;
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {

        RankNode root = null;

        int[] arr = { 820, 540, 910, 770, 880, 460, 990, 600, 730, 950, 510 };

        for (int x : arr) {
            root = insert(root, x);
        }

        // Updates from case study
        root = updateScore(root, 540, 815);
        root = updateScore(root, 910, 685);

        // Example rank query
        System.out.println("Rank of 770 = " + rankOf(root, 770));
    }
}