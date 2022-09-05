package net.koreate.greatescape.utils;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageMaker {
  private int totalCount;
  
  private int startPage;
  
  private int endPage;
  
  private boolean first;
  
  private boolean last;
  
  private boolean prev;
  
  private boolean next;
  
  private int displayPageNum = 5;
  
  private int maxPage;
  
  private Criteria cri;
  
  public PageMaker() {
    this(0);
  }
  
  public PageMaker(int totalCount) {
    this(new Criteria(), totalCount);
  }
  
  public PageMaker(Criteria cri, int totalCount) {
    setCri(cri);
    setTotalCount(totalCount);
  }
  
  public void calcPaging() {
    this.endPage = (int)Math.ceil(this.cri.getPage() / (double)this.displayPageNum) * this.displayPageNum;
    this.startPage = this.endPage - this.displayPageNum + 1;
    this.maxPage = (int)Math.ceil(this.totalCount / (double)this.cri.getPerPageNum());
    if (this.endPage > this.maxPage)
      this.endPage = this.maxPage; 
    this.first = (this.startPage > 1);
    this.last = (this.cri.getPage() < this.maxPage);
    this.prev = !(this.endPage - this.displayPageNum <= 0);
    this.next = !(this.endPage == this.maxPage);
  }
  
  public int getMaxPage() {
    return this.maxPage;
  }
  
  public int getTotalCount() {
    return this.totalCount;
  }
  
  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
    calcPaging();
  }
  
  public int getStartPage() {
    return this.startPage;
  }
  
  public int getEndPage() {
    return this.endPage;
  }
  
  public boolean isFirst() {
    return this.first;
  }
  
  public boolean isLast() {
    return this.last;
  }
  
  public boolean isPrev() {
    return this.prev;
  }
  
  public boolean isNext() {
    return this.next;
  }
  
  public int getDisplayPageNum() {
    return this.displayPageNum;
  }
  
  public void setDisplayPageNum(int displayPageNum) {
    if (this.cri == null)
      setCri(new Criteria()); 
    this.displayPageNum = displayPageNum;
    calcPaging();
  }
  
  public Criteria getCri() {
    return this.cri;
  }
  
  public void setCri(Criteria cri) {
    this.cri = cri;
    calcPaging();
  }
  
  public String makeQuery(int page) {
    UriComponents uriComponents = 
      UriComponentsBuilder.newInstance()
      .queryParam("page", new Object[] { Integer.valueOf(page) }).queryParam("perPageNum", new Object[] { Integer.valueOf(this.cri.getPerPageNum()) }).build();
    String query = uriComponents.toUriString();
    return query;
  }
  
  public String toString() {
    return "PageMaker [totalCount=" + this.totalCount + ", startPage=" + this.startPage + ", endPage=" + this.endPage + ", first=" + 
      this.first + ", last=" + this.last + ", prev=" + this.prev + ", next=" + this.next + ", displayPageNum=" + this.displayPageNum + 
      ", maxPage=" + this.maxPage + ", cri=" + this.cri + "]";
  }
}
