import React, { useState } from "react";
import styled from "styled-components";
import { getVariantColor, VariantColor } from "../../utils";

export type PieChartData = {
  name: string;
  value: number;
  variant?: VariantColor;
};

type CustomPieChartProps = {
  title: string;
  data: PieChartData[];
  showLegend?: boolean;
  size?: number;
};

const CustomPieChart: React.FC<CustomPieChartProps> = ({
  title,
  data,
  showLegend = true,
  size = 200,
}) => {
  const [hoveredItem, setHoveredItem] = useState<string | null>(null);

  const totalValue = data.reduce((sum, item) => sum + item.value, 0);
  let cumulativePercentage = 0;
  const coloredData = assignColors(data);

  return (
    <ChartWrapper>
      <ChartHeader>
        <ChartTitle>{title}</ChartTitle>
      </ChartHeader>

      <ChartContent>
        <SvgContainer width={size} height={size} viewBox="0 0 32 32">
          {coloredData.map((item) => {
            const percentage = (item.value / totalValue) * 100;
            const startAngle = (cumulativePercentage / 100) * 360;
            cumulativePercentage += percentage;
            const endAngle = (cumulativePercentage / 100) * 360;
            const pathData = describeArc(16, 16, 15, startAngle, endAngle);

            return (
              <Slice
                key={item.name}
                variant={item.variant!}
                d={pathData}
                onMouseEnter={() => setHoveredItem(`${item.name}: ${item.value}`)}
                onMouseLeave={() => setHoveredItem(null)}
              />
            );
          })}
        </SvgContainer>

        {showLegend && (
          <LegendContainer>
            {coloredData.map((item) => (
              <LegendItem key={item.name}>
                <LegendColor variant={item.variant!} />
                <LegendText>{item.name}</LegendText>
              </LegendItem>
            ))}
          </LegendContainer>
        )}
      </ChartContent>

      {hoveredItem && <Tooltip>{hoveredItem}</Tooltip>}
    </ChartWrapper>
  );
};

export default CustomPieChart;

function assignColors(data: PieChartData[]): PieChartData[] {
  const availableColors: VariantColor[] = ['success', 'info', 'warning', 'quaternary'];
  const colorUsageCount: Record<VariantColor, number> = {
    success: 0,
    info: 0,
    warning: 0,
    quaternary: 0,
    primary: 0,
    secondary: 0,
    tertiary: 0
  };

  return data.map((item) => {
    if (item.variant) {
      colorUsageCount[item.variant] = (colorUsageCount[item.variant] || 0) + 1;
      return item;
    }

    const sortedColors = availableColors.sort((a, b) => colorUsageCount[a] - colorUsageCount[b]);
    const leastUsedColor = sortedColors[0];
    colorUsageCount[leastUsedColor] += 1;

    return { ...item, variant: leastUsedColor };
  });
}

function polarToCartesian(centerX: number, centerY: number, radius: number, angleInDegrees: number) {
  const angleInRadians = ((angleInDegrees - 90) * Math.PI) / 180.0;
  return {
    x: centerX + radius * Math.cos(angleInRadians),
    y: centerY + radius * Math.sin(angleInRadians),
  };
}

function describeArc(x: number, y: number, radius: number, startAngle: number, endAngle: number): string {
  const start = polarToCartesian(x, y, radius, endAngle);
  const end = polarToCartesian(x, y, radius, startAngle);
  const largeArcFlag = endAngle - startAngle <= 180 ? "0" : "1";

  return [
    "M", start.x, start.y,
    "A", radius, radius, 0, largeArcFlag, 0, end.x, end.y,
    "L", x, y,
    "Z"
  ].join(" ");
}

const ChartWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
`;

const ChartHeader = styled.div`
  text-align: center;
  width: 100%;
  margin-bottom: 15px;
`;

const ChartTitle = styled.h2`
  font-size: 1.2rem;
  font-weight: bold;
  color: ${({ theme }) => theme.colors.white};

  @media (max-width: 768px) {
    font-size: 1rem;
  }
`;

const ChartContent = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  gap: 10px;
`;

const SvgContainer = styled.svg`
  display: block;
  flex-shrink: 0;
`;

const LegendContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  min-width: 50px;
`;

const LegendItem = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 4px;
  justify-content: flex-start;
`;

const LegendColor = styled.div<{ variant: VariantColor }>`
  width: 16px;
  height: 16px;
  background-color: ${({ variant, theme }) => getVariantColor(theme, variant)};
  border-radius: 4px;
`;

const LegendText = styled.span`
  color: ${({ theme }) => theme.colors.white};
  font-size: 14px;
`;

const Slice = styled.path<{ variant: VariantColor }>`
  fill: ${({ theme, variant }) => getVariantColor(theme, variant)};
  stroke: ${({ theme }) => theme.colors.gray};
  stroke-width: 0.5;
  transition: all 0.3s ease;
  cursor: pointer;

  &:hover {
    filter: brightness(1.5);
  }
`;

const Tooltip = styled.div`
  position: absolute;
  top: 10px;
  left: 50%;
  transform: translateX(-50%);
  background: rgba(0, 0, 0, 0.8);
  color: ${({ theme }) => theme.colors.white};
  padding: 8px 12px;
  border-radius: 4px;
  font-size: 14px;
  pointer-events: none;
  white-space: nowrap;
  z-index: 2;
`;
