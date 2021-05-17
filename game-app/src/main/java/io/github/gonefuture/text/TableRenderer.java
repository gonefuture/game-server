package io.github.gonefuture.text;

import java.io.OutputStream;
import java.io.Writer;

public interface TableRenderer {

    void render(OutputStream ps, int indent);

    void render(Writer w, int indent);


}
