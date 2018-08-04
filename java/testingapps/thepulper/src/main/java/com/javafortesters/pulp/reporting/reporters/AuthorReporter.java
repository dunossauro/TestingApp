package com.javafortesters.pulp.reporting.reporters;

import com.javafortesters.pulp.domain.objects.PulpAuthor;
import com.javafortesters.pulp.html.templates.FilledHTMLTemplate;
import com.javafortesters.pulp.html.templates.MyUrlEncoder;
import com.javafortesters.pulp.reporting.ReportConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthorReporter {
    private final ReportConfig reportConfig;

    public AuthorReporter(ReportConfig reportConfig) {
        this.reportConfig = new ReportConfig(reportConfig);
    }

    public AuthorReporter(ReportConfig reportConfig, boolean includeFaqLinks) {
        this.reportConfig = new ReportConfig(reportConfig);
        this.reportConfig.setIncludeFaqLinks(includeFaqLinks);
    }

    public Collection<String> getAsStrings(Collection<PulpAuthor> authors) {
        List<String> report = new ArrayList<>();

        for(PulpAuthor author : authors) {

            report.add(getAuthorName(author));
        }

        return report;
    }

    public String getAuthorName(PulpAuthor author) {

        if(reportConfig!=null && reportConfig.areAuthorNamesLinks()){
            String faqs="";
            if(reportConfig.includeFaqLinks()) {
                 faqs = String.format(" [<a href='%s%s?searchterm=%s'>faqs</a>]", reportConfig.getReportPath(),"author/faqs", MyUrlEncoder.encode(author.getName()));
            }

            String name =  String.format("<a href='%s?author=%s'>%s</a>", reportConfig.getReportPath("books"), author.getId(), author.getName());

            String amend="";
            if(reportConfig.areAuthorAmendLinksShown()) {
                amend = String.format("[<a href='%s%s' alt='Amend'>amend</a>]",
                        reportConfig.withoutPostLink().withoutReportInPath().getReportPath("amend/author?author="), author.getId(), author.getName());
            }

            return name + " "  + faqs + " " + amend;

        }else{
            return author.getName();
        }

    }

    public String getConcatenated(Collection<PulpAuthor> authors, String concatonator) {
        StringBuilder line = new StringBuilder();
        int authorCount = authors.size();

        for(PulpAuthor author : authors){
            line.append(getAuthorName(author));
            authorCount--;
            if(authorCount!=0) {
                line.append(concatonator);
            }
        }

        return line.toString();
    }

}
