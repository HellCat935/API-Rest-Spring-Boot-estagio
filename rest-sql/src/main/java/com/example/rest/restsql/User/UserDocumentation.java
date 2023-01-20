package com.example.rest.restsql.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserDocumentation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userDocId;

  @Column(nullable = false)
  private Byte document;

  @Column(nullable = false)
  private String docType;

  @Column(nullable = false)
  private String docUser;

  public Long getUserDocId() {
    return userDocId;
  }

  public void setUserDocId(Long userDocId) {
    this.userDocId = userDocId;
  }

  public Byte getDocument() {
    return document;
  }

  public void setDocument(Byte document) {
    this.document = document;
  }

  public String getDocType() {
    return docType;
  }

  public void setDocType(String docType) {
    this.docType = docType;
  }

  public String getDocUser() {
    return docUser;
  }

  public void setDocUser(String docUser) {
    this.docUser = docUser;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((userDocId == null) ? 0 : userDocId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    UserDocumentation other = (UserDocumentation) obj;
    if (userDocId == null) {
      if (other.userDocId != null) return false;
    } else if (!userDocId.equals(other.userDocId)) return false;
    return true;
  }
}
