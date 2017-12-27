package org.perftester.renderer

import java.nio.file.Files

import ammonite.ops._
import ammonite.ops.Path
import org.perftester.{EnvironmentConfig, TestConfig}
import org.perftester.results.RunResult

import scalatags.Text.all._

object HtmlRenderer {
  def outputHtmlResults(outputFolder: Path, envConfig: EnvironmentConfig, results: Seq[(TestConfig, RunResult)]): Unit = {



    val content  =html(
      head(
        script(`type` :="text/javascript", src := "https://www.gstatic.com/charts/loader.js"),
          raw(
            """
              |    <script type="text/javascript">
              |      google.charts.load('current', {'packages':['corechart']});
              |      google.charts.setOnLoadCallback(drawChart);
              |
              |      function drawChart() {
              |        var data = google.visualization.arrayToDataTable([
              |          ['Director (Year)',  'Rotten Tomatoes', 'IMDB'],
              |          ['Alfred Hitchcock (1935)', 8.4,         7.9],
              |          ['Ralph Thomas (1959)',     6.9,         6.5],
              |          ['Don Sharp (1978)',        6.5,         6.4],
              |          ['James Hawes (2008)',      4.4,         6.2]
              |        ]);
              |
              |        var options = {
              |          title: 'First run results'',
              |          vAxis: {title: 'Time (ms)'},
              |          isStacked: true
              |        };
              |
              |        var chart = new google.visualization.SteppedAreaChart(document.getElementById('chart_div'));
              |
              |        chart.draw(data, options);
              |      }
              |    </script>
            """.stripMargin

          )
      ),
      body(
        raw(
          """
          |    <div id="chart_div" style="width: 900px; height: 500px;"></div>
          """.stripMargin
        )
      ),
    )

    val path = outputFolder / "index.html"
    write.over(path, content.toString())

    println(s"open $path")
  }
}
