package com.hubspot.jinjava.el.ext;

import com.hubspot.jinjava.el.ext.eager.EagerAstNamedParameter;
import com.hubspot.jinjava.el.tree.impl.Parser.ExtensionHandler;
import com.hubspot.jinjava.el.tree.impl.Parser.ExtensionPoint;
import com.hubspot.jinjava.el.tree.impl.Scanner;
import com.hubspot.jinjava.el.tree.impl.ast.AstIdentifier;
import com.hubspot.jinjava.el.tree.impl.ast.AstNode;
import jakarta.el.ELException;

public class NamedParameterOperator {
  public static final Scanner.ExtensionToken TOKEN = new Scanner.ExtensionToken("=");

  public static final ExtensionHandler HANDLER = getHandler(false);

  public static ExtensionHandler getHandler(boolean eager) {
    return new ExtensionHandler(ExtensionPoint.ADD) {

      @Override
      public AstNode createAstNode(AstNode... children) {
        if (!(children[0] instanceof AstIdentifier)) {
          throw new ELException("Expected IDENTIFIER, found " + children[0].toString());
        }
        AstIdentifier name = (AstIdentifier) children[0];
        return eager
          ? new EagerAstNamedParameter(name, children[1])
          : new AstNamedParameter(name, children[1]);
      }
    };
  }
}
