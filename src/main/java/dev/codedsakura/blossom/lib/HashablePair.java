package dev.codedsakura.blossom.lib;

public class HashablePair<A, B> {
    A left;
    B right;

    public HashablePair(A left, B right) {
        this.left = left;
        this.right = right;
    }

    public A getLeft() {
        return this.left;
    }

    public void setLeft(A left) {
        this.left = left;
    }

    public B getRight() {
        return this.right;
    }

    public void setRight(B right) {
        this.right = right;
    }

    // https://www.baeldung.com/java-hashcode#standard-hashcode-implementations
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + left.hashCode();
        hash = 31 * hash + right.hashCode();
        return hash;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof HashablePair)) {
            return false;
        }

        HashablePair<A, B> that = (HashablePair<A, B>) obj;
        return this.left.equals(that.left) && this.right.equals(that.right);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object clone() throws CloneNotSupportedException {
        HashablePair<A, B> clone = (HashablePair<A, B>) super.clone();

        clone.left = this.left;
        clone.right = this.right;

        return clone;
    }

    @Override
    public String toString() {
        return String.format("Pair<%s, %s>", this.left.toString(), this.right.toString());
    }
}
