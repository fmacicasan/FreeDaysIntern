package org.apache.jsp.WEB_002dINF.views.profile;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class create_jspx extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(6);
    _jspx_dependants.add("/WEB-INF/tags/form/multi.tagx");
    _jspx_dependants.add("/WEB-INF/tags/util/panel.tagx");
    _jspx_dependants.add("/WEB-INF/tags/form/fields/select.tagx");
    _jspx_dependants.add("/WEB-INF/tags/form/fields/reference.tagx");
    _jspx_dependants.add("/WEB-INF/tags/form/fields/file.tagx");
    _jspx_dependants.add("/WEB-INF/tags/form/dependency.tagx");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fvar_005fcode_005farguments_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005farguments_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fvar_005fcode_005farguments_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005farguments_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
    _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fvar_005fcode_005farguments_005fnobody.release();
    _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005farguments_005fnobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<div version=\"2.0\">");
      if (_jspx_meth_form_005fmulti_005f0(_jspx_page_context))
        return;
      if (_jspx_meth_form_005fdependency_005f0(_jspx_page_context))
        return;
      out.write("</div>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_form_005fmulti_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  form:multi
    org.apache.jsp.tag.web.form.multi_tagx _jspx_th_form_005fmulti_005f0 = new org.apache.jsp.tag.web.form.multi_tagx();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_form_005fmulti_005f0);
    _jspx_th_form_005fmulti_005f0.setJspContext(_jspx_page_context);
    // /WEB-INF/views/profile/create.jspx(5,138) name = z type = java.lang.String reqTime = true required = false fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_form_005fmulti_005f0.setZ("user-managed");
    // /WEB-INF/views/profile/create.jspx(5,138) name = render type = java.lang.Boolean reqTime = true required = false fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_form_005fmulti_005f0.setRender((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${empty dependencies}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false));
    // /WEB-INF/views/profile/create.jspx(5,138) name = path type = java.lang.String reqTime = true required = true fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_form_005fmulti_005f0.setPath("/profile");
    // /WEB-INF/views/profile/create.jspx(5,138) name = modelAttribute type = java.lang.String reqTime = true required = true fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_form_005fmulti_005f0.setModelAttribute("profile");
    // /WEB-INF/views/profile/create.jspx(5,138) name = id type = java.lang.String reqTime = true required = true fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_form_005fmulti_005f0.setId("fc_freedays_domain_Profile");
    _jspx_th_form_005fmulti_005f0.setJspBody(new Helper( 0, _jspx_page_context, _jspx_th_form_005fmulti_005f0, null));
    _jspx_th_form_005fmulti_005f0.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_form_005fmulti_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f0(javax.servlet.jsp.tagext.JspTag _jspx_parent, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f0.setParent(new javax.servlet.jsp.tagext.TagAdapter((javax.servlet.jsp.tagext.SimpleTag) _jspx_parent));
    // /WEB-INF/views/profile/create.jspx(6,35) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${hasErrors}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
    if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_spring_005fmessage_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
          return true;
        out.write("<div class=\"errors\">");
        if (_jspx_meth_spring_005fmessage_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
          return true;
        out.write("</div>");
        int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
      throw new SkipPageException();
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
    return false;
  }

  private boolean _jspx_meth_spring_005fmessage_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  spring:message
    org.springframework.web.servlet.tags.MessageTag _jspx_th_spring_005fmessage_005f0 = (org.springframework.web.servlet.tags.MessageTag) _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fvar_005fcode_005farguments_005fnobody.get(org.springframework.web.servlet.tags.MessageTag.class);
    _jspx_th_spring_005fmessage_005f0.setPageContext(_jspx_page_context);
    _jspx_th_spring_005fmessage_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f0);
    // /WEB-INF/views/profile/create.jspx(7,103) name = var type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_spring_005fmessage_005f0.setVar("form_error_msg");
    // /WEB-INF/views/profile/create.jspx(7,103) name = code type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_spring_005fmessage_005f0.setCode("file_was_not_pdf");
    // /WEB-INF/views/profile/create.jspx(7,103) name = arguments type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_spring_005fmessage_005f0.setArguments((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${daytypeerror}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    int[] _jspx_push_body_count_spring_005fmessage_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_spring_005fmessage_005f0 = _jspx_th_spring_005fmessage_005f0.doStartTag();
      if (_jspx_th_spring_005fmessage_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        throw new SkipPageException();
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_spring_005fmessage_005f0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_spring_005fmessage_005f0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_spring_005fmessage_005f0.doFinally();
      _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fvar_005fcode_005farguments_005fnobody.reuse(_jspx_th_spring_005fmessage_005f0);
    }
    return false;
  }

  private boolean _jspx_meth_spring_005fmessage_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  spring:message
    org.springframework.web.servlet.tags.MessageTag _jspx_th_spring_005fmessage_005f1 = (org.springframework.web.servlet.tags.MessageTag) _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005farguments_005fnobody.get(org.springframework.web.servlet.tags.MessageTag.class);
    _jspx_th_spring_005fmessage_005f1.setPageContext(_jspx_page_context);
    _jspx_th_spring_005fmessage_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f0);
    // /WEB-INF/views/profile/create.jspx(9,94) name = code type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_spring_005fmessage_005f1.setCode("default_form_error_msg");
    // /WEB-INF/views/profile/create.jspx(9,94) name = arguments type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_spring_005fmessage_005f1.setArguments((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${form_error_msg}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    int[] _jspx_push_body_count_spring_005fmessage_005f1 = new int[] { 0 };
    try {
      int _jspx_eval_spring_005fmessage_005f1 = _jspx_th_spring_005fmessage_005f1.doStartTag();
      if (_jspx_th_spring_005fmessage_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        throw new SkipPageException();
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_spring_005fmessage_005f1[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_spring_005fmessage_005f1.doCatch(_jspx_exception);
    } finally {
      _jspx_th_spring_005fmessage_005f1.doFinally();
      _005fjspx_005ftagPool_005fspring_005fmessage_0026_005fcode_005farguments_005fnobody.reuse(_jspx_th_spring_005fmessage_005f1);
    }
    return false;
  }

  private boolean _jspx_meth_field_005fselect_005f0(javax.servlet.jsp.tagext.JspTag _jspx_parent, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  field:select
    org.apache.jsp.tag.web.form.fields.select_tagx _jspx_th_field_005fselect_005f0 = new org.apache.jsp.tag.web.form.fields.select_tagx();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_field_005fselect_005f0);
    _jspx_th_field_005fselect_005f0.setJspContext(_jspx_page_context);
    _jspx_th_field_005fselect_005f0.setParent(_jspx_parent);
    // /WEB-INF/views/profile/create.jspx(12,154) name = z type = java.lang.String reqTime = true required = false fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_field_005fselect_005f0.setZ("user-managed");
    // /WEB-INF/views/profile/create.jspx(12,154) name = path type = java.lang.String reqTime = true required = true fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_field_005fselect_005f0.setPath("/uadmin");
    // /WEB-INF/views/profile/create.jspx(12,154) name = items type = java.util.Collection reqTime = true required = true fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_field_005fselect_005f0.setItems((java.util.Collection) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${regularusers}", java.util.Collection.class, (PageContext)_jspx_page_context, null, false));
    // /WEB-INF/views/profile/create.jspx(12,154) name = itemValue type = java.lang.String reqTime = true required = false fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_field_005fselect_005f0.setItemValue("id");
    // /WEB-INF/views/profile/create.jspx(12,154) name = id type = java.lang.String reqTime = true required = true fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_field_005fselect_005f0.setId("c_freedays_app_FDUser_regularUser");
    // /WEB-INF/views/profile/create.jspx(12,154) name = field type = java.lang.String reqTime = true required = true fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_field_005fselect_005f0.setField("regularUser");
    _jspx_th_field_005fselect_005f0.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_field_005fselect_005f0);
    return false;
  }

  private boolean _jspx_meth_field_005ffile_005f0(javax.servlet.jsp.tagext.JspTag _jspx_parent, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  field:file
    org.apache.jsp.tag.web.form.fields.file_tagx _jspx_th_field_005ffile_005f0 = new org.apache.jsp.tag.web.form.fields.file_tagx();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_field_005ffile_005f0);
    _jspx_th_field_005ffile_005f0.setJspContext(_jspx_page_context);
    _jspx_th_field_005ffile_005f0.setParent(_jspx_parent);
    // /WEB-INF/views/profile/create.jspx(13,132) name = z type = java.lang.String reqTime = true required = false fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_field_005ffile_005f0.setZ("user-managed");
    // /WEB-INF/views/profile/create.jspx(13,132) name = required type = java.lang.Boolean reqTime = true required = false fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_field_005ffile_005f0.setRequired(new Boolean(true));
    // /WEB-INF/views/profile/create.jspx(13,132) name = id type = java.lang.String reqTime = true required = true fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_field_005ffile_005f0.setId("c_org_pragmatikroo_roodocman_domain_Document_content");
    // /WEB-INF/views/profile/create.jspx(13,132) name = field type = java.lang.String reqTime = true required = true fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_field_005ffile_005f0.setField("document.content");
    _jspx_th_field_005ffile_005f0.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_field_005ffile_005f0);
    return false;
  }

  private boolean _jspx_meth_form_005fdependency_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  form:dependency
    org.apache.jsp.tag.web.form.dependency_tagx _jspx_th_form_005fdependency_005f0 = new org.apache.jsp.tag.web.form.dependency_tagx();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_form_005fdependency_005f0);
    _jspx_th_form_005fdependency_005f0.setJspContext(_jspx_page_context);
    // /WEB-INF/views/profile/create.jspx(23,153) name = z type = java.lang.String reqTime = true required = false fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_form_005fdependency_005f0.setZ("wPQqDJNMPlRMsf5i43ZfeOmis2g=");
    // /WEB-INF/views/profile/create.jspx(23,153) name = render type = java.lang.Boolean reqTime = true required = false fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_form_005fdependency_005f0.setRender((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${not empty dependencies}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false));
    // /WEB-INF/views/profile/create.jspx(23,153) name = id type = java.lang.String reqTime = true required = true fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_form_005fdependency_005f0.setId("d_freedays_domain_Profile");
    // /WEB-INF/views/profile/create.jspx(23,153) name = dependencies type = java.util.Collection reqTime = true required = true fragment = false deferredValue = false expectedTypeName = java.lang.String deferredMethod = false methodSignature = null
    _jspx_th_form_005fdependency_005f0.setDependencies((java.util.Collection) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dependencies}", java.util.Collection.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_form_005fdependency_005f0.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_form_005fdependency_005f0);
    return false;
  }

  private class Helper
      extends org.apache.jasper.runtime.JspFragmentHelper
  {
    private javax.servlet.jsp.tagext.JspTag _jspx_parent;
    private int[] _jspx_push_body_count;

    public Helper( int discriminator, JspContext jspContext, javax.servlet.jsp.tagext.JspTag _jspx_parent, int[] _jspx_push_body_count ) {
      super( discriminator, jspContext, _jspx_parent );
      this._jspx_parent = _jspx_parent;
      this._jspx_push_body_count = _jspx_push_body_count;
    }
    public boolean invoke0( JspWriter out ) 
      throws Throwable
    {
      if (_jspx_meth_c_005fif_005f0(_jspx_parent, _jspx_page_context))
        return true;
      if (_jspx_meth_field_005fselect_005f0(_jspx_parent, _jspx_page_context))
        return true;
      if (_jspx_meth_field_005ffile_005f0(_jspx_parent, _jspx_page_context))
        return true;
      return false;
    }
    public void invoke( java.io.Writer writer )
      throws JspException
    {
      JspWriter out = null;
      if( writer != null ) {
        out = this.jspContext.pushBody(writer);
      } else {
        out = this.jspContext.getOut();
      }
      try {
        this.jspContext.getELContext().putContext(JspContext.class,this.jspContext);
        switch( this.discriminator ) {
          case 0:
            invoke0( out );
            break;
        }
      }
      catch( Throwable e ) {
        if (e instanceof SkipPageException)
            throw (SkipPageException) e;
        throw new JspException( e );
      }
      finally {
        if( writer != null ) {
          this.jspContext.popBody();
        }
      }
    }
  }
}
