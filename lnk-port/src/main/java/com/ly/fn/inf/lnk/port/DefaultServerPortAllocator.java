package com.ly.fn.inf.lnk.port;

import java.net.ServerSocket;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ly.fn.inf.lnk.api.Application;
import com.ly.fn.inf.lnk.api.port.ServerPortAllocator;
import com.ly.fn.inf.lnk.remoting.utils.RemotingUtils;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2017年5月24日 下午7:15:42
 */
public class DefaultServerPortAllocator implements ServerPortAllocator, PortDetector {
    private static final Logger log = LoggerFactory.getLogger(DefaultServerPortAllocator.class.getSimpleName());
    private static final int[] COMMON_LISTEN_PORTS = new int[] {1030, 1031, 1032, 1047, 1048, 1058, 1059, 1067, 1068, 1080, 1083, 1084, 1099, 1123, 1155, 1212, 1222, 1239, 1248, 1313, 1314, 1321,
            1345, 1346, 1347, 1348, 1349, 1350, 1352, 1353, 1354, 1355, 1356, 1357, 1358, 1359, 1360, 1361, 1362, 1363, 1364, 1365, 1366, 1367, 1368, 1369, 1370, 1371, 1372, 1373, 1374, 1375, 1376,
            1377, 1378, 1379, 1380, 1381, 1382, 1383, 1384, 1385, 1386, 1387, 1388, 1389, 1390, 1391, 1392, 1393, 1394, 1395, 1396, 1399, 1400, 1402, 1403, 1404, 1405, 1406, 1407, 1408, 1409, 1410,
            1411, 1412, 1413, 1414, 1415, 1416, 1417, 1418, 1419, 1420, 1421, 1422, 1423, 1424, 1425, 1426, 1427, 1428, 1429, 1430, 1431, 1432, 1433, 1434, 1435, 1436, 1437, 1438, 1439, 1440, 1441,
            1442, 1443, 1444, 1445, 1446, 1447, 1448, 1449, 1450, 1451, 1452, 1453, 1454, 1455, 1456, 1457, 1458, 1459, 1460, 1461, 1462, 1463, 1464, 1465, 1466, 1467, 1468, 1469, 1470, 1471, 1472,
            1473, 1474, 1475, 1476, 1477, 1478, 1479, 1480, 1481, 1482, 1483, 1484, 1485, 1486, 1487, 1488, 1489, 1490, 1491, 1492, 1493, 1494, 1495, 1496, 1497, 1498, 1499, 1500, 1501, 1502, 1503,
            1504, 1505, 1506, 1507, 1508, 1509, 1510, 1511, 1512, 1513, 1514, 1515, 1516, 1517, 1518, 1519, 1520, 1521, 1522, 1523, 1525, 1526, 1527, 1528, 1529, 1530, 1531, 1532, 1533, 1534, 1535,
            1536, 1537, 1538, 1539, 1540, 1541, 1542, 1543, 1544, 1545, 1546, 1547, 1548, 1549, 1550, 1551, 1552, 1553, 1554, 1555, 1556, 1557, 1558, 1559, 1560, 1561, 1562, 1563, 1564, 1565, 1566,
            1567, 1568, 1569, 1570, 1571, 1572, 1573, 1574, 1575, 1576, 1577, 1578, 1579, 1580, 1581, 1582, 1583, 1584, 1585, 1586, 1587, 1588, 1589, 1590, 1591, 1592, 1593, 1594, 1595, 1596, 1597,
            1598, 1599, 1600, 1601, 1602, 1603, 1604, 1605, 1606, 1607, 1608, 1609, 1610, 1611, 1612, 1613, 1614, 1615, 1616, 1617, 1618, 1619, 1620, 1621, 1622, 1623, 1624, 1625, 1636, 1637, 1638,
            1639, 1640, 1641, 1642, 1643, 1645, 1646, 1647, 1648, 1649, 1650, 1651, 1652, 1653, 1654, 1655, 1656, 1657, 1658, 1659, 1660, 1661, 1662, 1663, 1664, 1665, 1666, 1667, 1668, 1669, 1670,
            1671, 1672, 1673, 1674, 1675, 1676, 1677, 1678, 1679, 1681, 1682, 1683, 1684, 1685, 1686, 1687, 1688, 1689, 1690, 1691, 1692, 1693, 1694, 1695, 1696, 1697, 1698, 1699, 1700, 1701, 1702,
            1703, 1704, 1705, 1706, 1707, 1708, 1709, 1710, 1711, 1712, 1713, 1714, 1715, 1716, 1717, 1718, 1719, 1720, 1721, 1722, 1723, 1724, 1725, 1726, 1727, 1728, 1729, 1730, 1731, 1732, 1733,
            1734, 1735, 1736, 1737, 1738, 1739, 1740, 1741, 1742, 1743, 1744, 1745, 1746, 1747, 1748, 1749, 1750, 1751, 1752, 1753, 1754, 1755, 1756, 1757, 1758, 1759, 1760, 1761, 1762, 1763, 1764,
            1765, 1766, 1767, 1768, 1769, 1770, 1771, 1772, 1773, 1774, 1776, 1777, 1779, 1780, 1781, 1782, 1783, 1784, 1785, 1786, 1787, 1788, 1789, 1790, 1791, 1792, 1793, 1794, 1795, 1796, 1797,
            1798, 1799, 1801, 1804, 1807, 1812, 1813, 1814, 1815, 1816, 1818, 1819, 1820, 1821, 1822, 1823, 1824, 1850, 1863, 1865, 1901, 1902, 1903, 1904, 1905, 1906, 1908, 1911, 1913, 1914, 1915,
            1916, 1917, 1921, 1944, 1945, 1946, 1947, 1948, 1949, 1950, 1951, 1973, 1985, 1986, 1987, 1988, 1989, 1990, 1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998, 1999, 2000, 2001, 2002, 2004,
            2005, 2007, 2012, 2013, 2014, 2015, 2025, 2030, 2032, 2033, 2035, 2040, 2042, 2044, 2045, 2046, 2047, 2049, 2065, 2067, 2090, 2091, 2102, 2103, 2104, 2105, 2200, 2201, 2213, 2222, 2232,
            2234, 2236, 2240, 2241, 2242, 2279, 2280, 2281, 2282, 2283, 2284, 2285, 2286, 2287, 2288, 2300, 2307, 2308, 2313, 2316, 2321, 2322, 2323, 2326, 2327, 2329, 2330, 2333, 2337, 2344, 2345,
            2356, 2358, 2390, 2396, 2397, 2398, 2401, 2407, 2412, 2415, 2418, 2421, 2423, 2427, 2428, 2429, 2430, 2432, 2436, 2438, 2443, 2445, 2447, 2449, 2451, 2458, 2500, 2501, 2528, 2529, 2532,
            2592, 2628, 2634, 2636, 2639, 2641, 2642, 2646, 2653, 2655, 2656, 2700, 2784, 2785, 2786, 2787, 2788, 2789, 2908, 2912, 2971, 2974, 2975, 3000, 3001, 3003, 3010, 3012, 3020, 3047, 3048,
            3049, 3105, 3130, 3141, 3143, 3147, 3264, 3266, 3267, 3268, 3273, 3275, 3279, 3281, 3284, 3285, 3286, 3288, 3289, 3290, 3291, 3293, 3294, 3296, 3299, 3304, 3306, 3309, 3313, 3314, 3315,
            3318, 3319, 3321, 3326, 3327, 3328, 3329, 3330, 3333, 3338, 3339, 3340, 3341, 3342, 3351, 3352, 3353, 3354, 3362, 3372, 3378, 3379, 3381, 3383, 3390, 3391, 3392, 3395, 3421, 3455, 3456,
            3457, 3900, 3984, 3985, 3986, 4000, 4001, 4008, 4096, 4132, 4133, 4143, 4321, 4343, 4346, 4348, 4444, 4446, 4449, 4450, 4451, 4452, 4500, 4501, 4546, 4672, 4800, 4801, 4802, 4827, 4868,
            4885, 5002, 5003, 5004, 5005, 5010, 5020, 5021, 5050, 5060, 5150, 5190, 5191, 5192, 5193, 5272, 5300, 5301, 5304, 5305, 5307, 5310, 5311, 5400, 5402, 5404, 5407, 5409, 5410, 5411, 5414,
            5418, 5419, 5500, 5501, 5502, 5503, 5504, 5555, 5602, 5603, 5631, 5632, 5678, 5679, 5713, 5714, 5715, 5717, 5729, 5741, 5742, 5745, 5746, 5755, 5757, 5766, 5767, 6000, 6110, 6111, 6112,
            6123, 6141, 6142, 6143, 6144, 6145, 6146, 6147, 6148, 6149, 6253, 6389, 6500, 6558, 6665, 6670, 6672, 6673, 6790, 6831, 6969, 7010, 7020, 7070, 7099, 7100, 7121, 7174, 7200, 7201, 7395,
            7426, 7430, 7431, 7491, 7511, 7544, 7545, 7588, 7626, 7777, 7781, 7932, 7933, 7999, 8000, 8032, 8400, 8401, 8402, 8403, 8450, 8473, 8888, 8889, 8890, 8891, 8892, 8893, 8894, 9000, 9006,
            9090, 9535, 9594, 9595, 9876, 9992, 9993, 9994, 9995, 9996, 9997, 9998, 9999, 10000, 11000, 11001, 12753, 13160, 13720, 13721, 13782, 17219, 18000, 19410, 19411, 19541, 21845, 21846,
            21847, 21848, 21849, 22273, 22555, 22800, 22951, 24000, 24004, 24005, 25000, 25001, 25002, 25003, 25004, 25005, 25006, 25007, 25008, 25009, 25793, 26000, 26208, 45678, 47557, 47806,
            47808};

    private Properties env;
    private String appHost;
    private String appName;
    private String appInstanceNum;
    private String ip;

    public DefaultServerPortAllocator() {
        super();
        ip = RemotingUtils.getLocalAddress();
        env = new Properties(System.getProperties());
        Map<String, String> senv = System.getenv();
        for (Map.Entry<String, String> e : senv.entrySet()) {
            env.setProperty(e.getKey(), e.getValue());
        }
        appHost = StringUtils.defaultString(env.getProperty("host.name"), ip);
        appName = StringUtils.defaultString(env.getProperty("app.name"), "app");
        appInstanceNum = StringUtils.defaultString(env.getProperty("ins.num"), "1");
    }

    @Override
    public int selectPort(int expectListenPort, Application application) {
        if (isAvailable(expectListenPort)) {
            return expectListenPort;
        }
        int systemPortAllocator = NumberUtils.toInt(System.getProperty(appHost + "." + appName + "." + appInstanceNum + ".port"), -1);
        if (isAvailable(systemPortAllocator)) {
            return systemPortAllocator;
        }
        int i = 5;
        int roundListenPort = systemPortAllocator;
        roundListenPort++;
        do {
            if (isAvailable(roundListenPort)) {
                return roundListenPort;
            }
            roundListenPort++;
            i--;
        } while (i >= 0);
        throw new RuntimeException("application : " + application.getApp() + " expectListenPort : " + expectListenPort + " systemPortAllocator : " + systemPortAllocator + " ServerListenPort Allocator Error.");
    }

    @Override
    public boolean isAvailable(int listenPort) {
        if (listenPort <= 1023) {
            log.info("listenPort : {} is not Available.", listenPort);
            return false;
        }
        if (Arrays.binarySearch(COMMON_LISTEN_PORTS, listenPort) >= 0) {
            log.info("listenPort : {} is not Available.", listenPort);
            return false;
        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(listenPort, 1);
            return true;
        } catch (Throwable e) {
            log.warn("detection {} isAvailable Error : {}", listenPort, e.getLocalizedMessage());
        } finally {
            if (server != null) {
                try {
                    server.close();
                    server = null;
                } catch (Throwable e) {
                    log.info("close ServerSocket bind on {} failed.", listenPort);
                    return false;
                }
            }
        }
        log.info("listenPort : {} is not Available.", listenPort);
        return false;
    }
}
