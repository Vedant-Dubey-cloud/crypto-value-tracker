let cryptoList = [];
let chart;
let selectedIndex = -1;

// On page load
window.onload = async function () {
  await loadCryptoList();
  await loadTopCoins(); // load sidebar coins
};
const API_BASE = window.BACKEND_URL || "https://crypto-value-tracker.onrender.com/api/v1/crypto";


// 🔹 Fetch all coin names for suggestions
async function loadCryptoList() {
  try {
    const response = await fetch("http://localhost:9090/api/v1/crypto/all");
    const data = await response.json();
    cryptoList = data.data.map(c => c.symbol.toLowerCase());
    console.log(`✅ Loaded ${cryptoList.length} coins`);
  } catch (error) {
    console.error("❌ Failed to load coin list:", error);
  }
}

// 🔹 Always-visible Top 5 (sidebar)
async function loadTopCoins() {
  const container = document.getElementById("topCoinsList");
  container.innerHTML = "<p>Loading...</p>";

  try {
    const response = await fetch("http://localhost:9090/api/v1/crypto/top/coins");
    const data = await response.json();

    const rows = data.data
      .map(
        (coin, i) =>
          `<div class="coin-item"><strong>${i + 1}. ${coin.name}</strong><br/> $${coin.quote.USD.price.toFixed(2)}</div>`
      )
      .join("");
    container.innerHTML = rows;
  } catch (error) {
    console.error("❌ Error fetching top coins:", error);
    container.innerHTML = "<p>Error loading</p>";
  }
}

// 🔹 Search function
async function searchCrypto() {
  const symbol = document.getElementById("cryptoName").value.trim().toUpperCase();
  const title = document.getElementById("cryptoTitle");
  const price = document.getElementById("cryptoPrice");
  const resultBox = document.getElementById("result");

  if (!symbol) {
    alert("Please enter a crypto symbol like BTC or ETH");
    return;
  }

  title.textContent = "Fetching data...";
  price.textContent = "";
  resultBox.style.display = "block";

  try {
    const response = await fetch(`http://localhost:9090/api/v1/crypto/${symbol}`);
    if (!response.ok) throw new Error("Crypto not found");

    const data = await response.json();
    const coin = Object.values(data.data)[0];
    const coinName = coin.name || symbol;
    const currentPrice = coin.quote?.USD?.price?.toFixed(2) || "N/A";

    title.textContent = `${coinName} (${symbol})`;
    price.textContent = ` $${currentPrice}`;

    const prices = [
      currentPrice * 0.95,
      currentPrice * 0.97,
      currentPrice * 0.99,
      currentPrice,
    ];
    const labels = ["3h ago", "2h ago", "1h ago", "Now"];
    renderChart(prices, labels);
  } catch (error) {
    console.error("❌ Error fetching crypto:", error);
    title.textContent = "Error fetching data";
    price.textContent = "";
  }
}

// 🔹 Chart
function renderChart(prices, labels) {
  const ctx = document.getElementById("cryptoChart").getContext("2d");
  if (chart) chart.destroy();

  chart = new Chart(ctx, {
    type: "line",
    data: {
      labels,
      datasets: [
        {
          label: "Price (USD)",
          data: prices,
          borderColor: "#00b4d8",
          backgroundColor: "rgba(0, 180, 216, 0.2)",
          borderWidth: 2,
          tension: 0.3,
          fill: true,
          pointRadius: 3,
          pointHoverRadius: 6,
        },
      ],
    },
    options: {
      responsive: true,
      plugins: { legend: { display: false } },
      scales: {
        x: { ticks: { color: "#ccc" } },
        y: { ticks: { color: "#ccc" } },
      },
    },
  });
}

// 🔹 Suggestions
function showSuggestions(query) {
  const box = document.getElementById("suggestions");
  if (!query) {
    box.style.display = "none";
    return;
  }

  const matches = cryptoList.filter(c =>
    c.toLowerCase().includes(query.toLowerCase())
  );

  if (matches.length === 0) {
    box.innerHTML = `<div class="no-suggestion">No matches found</div>`;
    box.style.display = "block";
    return;
  }

  box.innerHTML = matches
    .slice(0, 10)
    .map(
      c =>
        `<div class="suggestion-item" onclick="selectSuggestion('${c}')">${c.toUpperCase()}</div>`
    )
    .join("");

  box.style.display = "block";
}

function selectSuggestion(coin) {
  document.getElementById("cryptoName").value = coin;
  document.getElementById("suggestions").style.display = "none";
  searchCrypto();
}

// 🔹 Keyboard navigation for dropdown
document.getElementById("cryptoName").addEventListener("keydown", function (event) {
  const suggestions = document.querySelectorAll(".suggestion-item");
  if (suggestions.length === 0) return;

  if (event.key === "ArrowDown") {
    selectedIndex = (selectedIndex + 1) % suggestions.length;
    updateHighlight(suggestions);
  } else if (event.key === "ArrowUp") {
    selectedIndex = (selectedIndex - 1 + suggestions.length) % suggestions.length;
    updateHighlight(suggestions);
  } else if (event.key === "Enter") {
    event.preventDefault();
    if (selectedIndex >= 0 && suggestions[selectedIndex]) {
      suggestions[selectedIndex].click();
    } else {
      searchCrypto();
    }
  }
});

function updateHighlight(suggestions) {
  suggestions.forEach((item, i) => {
    item.classList.toggle("highlighted", i === selectedIndex);
  });
}

// Expose globally
window.showSuggestions = showSuggestions;
window.selectSuggestion = selectSuggestion;
window.searchCrypto = searchCrypto;
window.loadTopCoins = loadTopCoins;
