package az.demo.bridge.elasticsearch.repo;

public interface Creator<T> {
    T create(T data);
}
