export type JobErrorLog = {
  id: number;
  jobName: string;
  errorTimestamp: string;
  errorMessage: string;
  stackTrace: string;
};