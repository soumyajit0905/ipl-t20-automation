package uiautomation.iplt20.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class IPLPointsTable extends BaseTest {

	@Test
	public void extractPointsTableData() {
		// Wait until the table is visible
		WebElement table = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".points-table-page")));

		// Extract column headers
		List<WebElement> headers = table.findElements(By.xpath("//tbody//th"));
		System.out.println("Table Headers:");
		for (WebElement header : headers) {
			System.out.print(header.getText() + "\t");
		}
		System.out.println();

		// Extract rows from the table body
		List<WebElement> rows = table.findElements(By.xpath("//tbody//tr"));
		System.out.println("\nTable Data:");

		for (WebElement row : rows) {
			List<WebElement> cols = row.findElements(By.xpath(".//td"));
			for (WebElement col : cols) {
				System.out.print(col.getText() + "\t");
			}
			System.out.println();
		}

		// Validate the number of rows (for example, checking if there are 10 teams in
		// the points table)
		Assert.assertTrue(rows.size() > 0, "Table data should be available");
	}

	@Test
	public void extractPointsTableDataByGrouping() {
		// Wait until the table is visible
		WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".points-table-page")));

		// Extract column headers
		List<WebElement> headers = table.findElements(By.xpath("//tbody//th"));
		System.out.println("Table Headers:");
		for (WebElement header : headers) {
			System.out.print(header.getText() + "\t");
		}
		System.out.println();

		// Extract rows from the table body
		List<WebElement> rows = table.findElements(By.cssSelector("tbody[id='pointsdata'] tr"));
		Map<String, List<String>> pointsTableMap = new TreeMap<>(Collections.reverseOrder()); // For sorting by points
																								// in descending order

		System.out.println("\nGrouped Points Data:");
		for (WebElement row : rows) {
			List<WebElement> cols = row.findElements(By.tagName("td"));
			String teamName = cols.get(2).findElement(By.tagName("h2")).getText(); // Extract Team name
			String points = cols.get(10).getText(); // Extract Points

			// Group teams by points
			pointsTableMap.computeIfAbsent(points, k -> new ArrayList<>()).add(teamName);
		}

		// Print grouped team data by points
		for (Map.Entry<String, List<String>> entry : pointsTableMap.entrySet()) {
			System.out.println(entry.getKey() + "\t" + String.join(", ", entry.getValue()));
		}

		System.out.println("\nUI Format Data:");
		// Now let's print the UI data format with detailed columns
		for (WebElement row : rows) {
			List<WebElement> cols = row.findElements(By.xpath(".//td"));
			String pos = cols.get(0).getText();
			String teamName = cols.get(2).getText();
			String played = cols.get(3).getText();
			String won = cols.get(4).getText();
			String lost = cols.get(5).getText();
			String nr = cols.get(6).getText();
			String nrr = cols.get(7).getText();
			String forRuns = cols.get(8).getText();
			String againstRuns = cols.get(9).getText();
			String points = cols.get(10).getText();
			String recentForm = cols.size() > 11 ? cols.get(11).getText() : "N/A"; // Check if the recent form column
																					// exists

			System.out.println(pos + "\t" + teamName + "\t" + played + "\t" + won + "\t" + lost + "\t" + nr + "\t" + nrr
					+ "\t" + forRuns + "\t" + againstRuns + "\t" + points + "\t" + recentForm);
		}

		// Validate the number of rows (for example, checking if there are 10 teams in
		// the points table)
		Assert.assertTrue(rows.size() > 0, "Table data should be available");
	}

}
